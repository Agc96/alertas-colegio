-- USE alertas_colegio;

INSERT INTO sa_usuario (nombre_usuario, contrasenia, dni, nombres, apellidos) VALUES ('admin', '$2a$10$Jybn33yG15zKhL24yHVnIuZAr3VriFjnWyMefbRroB1SxqX4QFaFS', '00000000', 'Administrador', 'Principal');
INSERT INTO sa_usuario_rol (nombre_usuario, rol) VALUES ('admin', 'ADMIN');

INSERT INTO sa_anio (id_anio) VALUES (2020);
INSERT INTO sa_anio (id_anio) VALUES (2021);

INSERT INTO sa_grado (nombre) VALUES ('1° primaria');
INSERT INTO sa_grado (nombre) VALUES ('2° primaria');
INSERT INTO sa_grado (nombre) VALUES ('3° primaria');
INSERT INTO sa_grado (nombre) VALUES ('4° primaria');
INSERT INTO sa_grado (nombre) VALUES ('5° primaria');
INSERT INTO sa_grado (nombre) VALUES ('6° primaria');
