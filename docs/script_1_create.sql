USE alertas_colegio;

-- Mantenimiento de usuarios

CREATE TABLE sa_usuario(
	nombre_usuario varchar(255) PRIMARY KEY,
	contrasenia varchar(255) NOT NULL,
	dni varchar(255) NOT NULL,
	nombres varchar(255) NOT NULL,
	apellidos varchar(255) NOT NULL
);

CREATE TABLE sa_usuario_rol(
	id_usuario_rol int IDENTITY(1,1) PRIMARY KEY,
	nombre_usuario varchar(255) NOT NULL FOREIGN KEY REFERENCES sa_usuario(nombre_usuario),
	rol varchar(255) NOT NULL
);

-- Mantenimiento de alumnos

CREATE TABLE sa_alumno(
	id_alumno int IDENTITY(1,1) PRIMARY KEY,
	dni varchar(255) NOT NULL,
	nombres varchar(255) NOT NULL,
	apellidos varchar(255) NOT NULL,
	fecha_nacimiento datetime NULL,
	padre varchar(255) NOT NULL FOREIGN KEY REFERENCES sa_usuario(nombre_usuario)
);

-- Asignación de aulas

CREATE TABLE sa_anio(
	id_anio int PRIMARY KEY
);

CREATE TABLE sa_grado(
	id_grado int IDENTITY(1,1) PRIMARY KEY,
	nombre varchar(255) NOT NULL
);

CREATE TABLE sa_aula(
	id_aula int IDENTITY(1,1) PRIMARY KEY,
	id_anio int NOT NULL FOREIGN KEY REFERENCES sa_anio(id_anio),
	id_grado int NOT NULL FOREIGN KEY REFERENCES sa_grado(id_grado),
	docente varchar(255) NOT NULL FOREIGN KEY REFERENCES sa_usuario(nombre_usuario)
);

CREATE TABLE sa_aula_alumno(
	id_aula int NOT NULL FOREIGN KEY REFERENCES sa_aula(id_aula),
	id_alumno int NOT NULL FOREIGN KEY REFERENCES sa_alumno(id_alumno)
);

-- Asistencia del alumno

CREATE TABLE sa_asistencia(
	id_asistencia int IDENTITY(1,1) PRIMARY KEY,
	id_aula int NOT NULL FOREIGN KEY REFERENCES sa_aula(id_aula),
	fecha datetime NOT NULL
);

CREATE TABLE sa_detalle_asistencia(
	id_detalle_asistencia int IDENTITY(1,1) PRIMARY KEY,
	id_asistencia int NOT NULL FOREIGN KEY REFERENCES sa_asistencia(id_asistencia),
	id_alumno int NOT NULL FOREIGN KEY REFERENCES sa_alumno(id_alumno),
	estado_asistencia varchar(255) NOT NULL
);

-- Comunicados del aula

CREATE TABLE sa_comunicado(
	id_comunicado int IDENTITY(1,1) PRIMARY KEY,
	id_aula int NOT NULL FOREIGN KEY REFERENCES sa_aula(id_aula),
	titulo varchar(255) NOT NULL,
	fecha datetime NOT NULL,
	descripcion varchar(1000) NOT NULL
);

-- Incidencias del alumno

CREATE TABLE sa_incidencia(
	id_incidencia int IDENTITY(1,1) PRIMARY KEY,
	id_aula int NOT NULL FOREIGN KEY REFERENCES sa_aula(id_aula),
	id_alumno int NOT NULL FOREIGN KEY REFERENCES sa_alumno(id_alumno),
	fecha datetime NOT NULL,
	descripcion varchar(1000) NOT NULL
);

-- Entrevistas del padre del alumno

CREATE TABLE sa_entrevista(
	id_entrevista int IDENTITY(1,1) PRIMARY KEY,
	id_aula int NOT NULL FOREIGN KEY REFERENCES sa_aula(id_aula),
	id_alumno int NOT NULL FOREIGN KEY REFERENCES sa_alumno(id_alumno),
	fecha datetime NOT NULL,
	motivo varchar(1000) NOT NULL
);

-- Notificaciones push

CREATE TABLE sa_suscripcion(
	endpoint varchar(255) PRIMARY KEY,
	public_key varchar(255) NOT NULL,
	auth varchar(255) NOT NULL,
	nombre_usuario varchar(255) NOT NULL FOREIGN KEY REFERENCES sa_usuario(nombre_usuario)
);
