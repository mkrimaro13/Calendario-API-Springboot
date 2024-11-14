CREATE USER docker;
--Ejecutar segundo
CREATE DATABASE calendario; 
GRANT ALL PRIVILEGES ON DATABASE calendario TO docker;
--Para las siguientes instrucciones, se debe cambiar la conexión
\c calendario
--Crear la tabla TIPO
CREATE TABLE Tipo(
	Id SERIAL PRIMARY KEY,
	Tipo VARCHAR(100) NOT NULL
	);

/* Crear indice para TIPO
	ordenado por FECHA */
CREATE UNIQUE INDEX ixTipo
	ON Tipo(Tipo);

--Crear la tabla CALENDARIO
CREATE TABLE Calendario(
	Id SERIAL PRIMARY KEY,
	Fecha DATE NOT NULL,
	IdTipo INT NOT NULL,
    CONSTRAINT fkCalendario_Tipo FOREIGN KEY (IdTipo) REFERENCES Tipo(Id),
    Descripcion VARCHAR(100) NULL
	);
    
/* Crear indice para CALENDARIO
	ordenado por FECHA */
CREATE UNIQUE INDEX ixCalendario
	ON Calendario(Fecha);
    