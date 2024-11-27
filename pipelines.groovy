pipeline {
    agent any
    environment {
        REPO_URL =
'https://github.com/mkrimaro13/Calendario-API-Springboot.git'
        BRANCH = 'jenkins' // Cambia esto si usas otra rama
        DOCKER_NETWORK = 'redfestivoscalendario'
        DOCKER_IMAGE = 'apicalendario:latest'
        DOCKER_CONTAINER = 'apicalendario'
    }
    stages {
        stage('Clonar Repositorio') {
            steps {
                git branch: "${BRANCH}", credentialsId: '100', url: "${REPO_URL}"
            }
        }
        stage('Verificar si existe la red y crearla si es necesario') {
            steps {
                script {
                    bat 'docker network list --filter "name=%DOCKER_NETWORK%" | findstr . || docker network create %DOCKER_NETWORK% || echo No hay red'
                }
            }
        }
        stage('Construir Imagen Docker') {
            steps {
                script {
                    bat 'docker build -t %DOCKER_IMAGE% .'
                }
            }
        }
        stage('Detener Contenedor Anterior') {
            steps {
                script {
                    bat '''
                    docker ps -q --filter "name=%DOCKER_CONTAINER%" | findstr . && docker stop %DOCKER_CONTAINER% || echo No hay contenedor en ejecución
                    docker ps -a -q --filter "name=%DOCKER_CONTAINER%" | findstr . && docker rm %DOCKER_CONTAINER% || echo No hay contenedor detenido
                    '''
                }
            }
        }
        stage('Desplegar Contenedor Docker') {
            steps {
                script {
                    bat 'docker container run --network %DOCKER_NETWORK% --name %DOCKER_CONTAINER% -p 8081:8080 -d %DOCKER_IMAGE%'
                }
            }
        }
    }
    post {
        success {
            echo 'Despliegue exitoso.'
        }
        failure {
            echo 'Despliegue fallido.'
        }
    }
}
