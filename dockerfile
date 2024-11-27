# Usar una imagen base de OpenJDK 21 para Java
FROM eclipse-temurin:17
# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app
# Copiar el archivo JAR de la aplicación Spring Boot al contenedor
COPY target/calendar-0.0.1-SNAPSHOT.jar /app/calendar-0.0.1-SNAPSHOT.jar
# Expone el puerto 8080 en el contenedor
EXPOSE 8081
# Definir el comando que se ejecutará cuando se inicie el contenedor
CMD ["java", "-jar", "calendar-0.0.1-SNAPSHOT.jar"]
