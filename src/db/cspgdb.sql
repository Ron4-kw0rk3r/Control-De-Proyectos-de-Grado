-- Database: cspgDB
-- by Brayan Ronaldo Sanchez Mendoza
-- dev out: Sanchezbt

-- update database 19/10/24 by brayan ronaldo

-- DROP DATABASE IF EXISTS "cspgDB";

CREATE DATABASE "cspgDB"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'C'
    LC_CTYPE = 'C'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

COMMENT ON DATABASE "cspgDB"
    IS 'Desarrollado en UNIX 19/10/24 
database system cspg full description on webpage 
cspg-usb.com ';

-- Base de la base de datos CSPG

CREATE TABLE Usuarios (
    usuario_id SERIAL PRIMARY KEY,
    correo_univ VARCHAR(60) UNIQUE NOT NULL,
    hash_contraseña VARCHAR(50) NOT NULL,
    nombre_completo VARCHAR(50) NOT NULL,
    role_id INT NOT NULL,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    actualizado_up TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- Crear la tabla login_usuarios
CREATE TABLE login_usuarios (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL CHECK (rol IN ('ESTUDIANTE', 'DOCENTE', 'ADMIN', 'ROOT_ADMIN')),
    activo BOOLEAN DEFAULT true,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultimo_acceso TIMESTAMP
);

-- Crear la tabla usersession
CREATE TABLE login_usersession (
    id SERIAL PRIMARY KEY,
    session_id VARCHAR(255) UNIQUE NOT NULL,
    usuario_id BIGINT REFERENCES usuarios(id),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultimo_acceso TIMESTAMP,
    activa BOOLEAN DEFAULT true
);

-- Crear índices para mejorar el rendimiento
CREATE INDEX idx_usuarios_email ON login_usuarios(email);
CREATE INDEX idx_usuarios_rol ON login_usuarios(rol);
CREATE INDEX idx_usersession_usuario ON login_usersession(usuario_id);
CREATE INDEX idx_usersession_activa ON login_usersession(activa);

-- Insertar algunos usuarios de prueba
-- ROOT ADMIN
INSERT INTO login_usuarios (email, nombre, apellido, rol, activo, fecha_registro)
VALUES ('usb.root.2024@root.usalesiana.edu.bo', 'Root', 'Administrator', 'ROOT_ADMIN', true, CURRENT_TIMESTAMP);

-- ADMIN
INSERT INTO login_usuarios (email, nombre, apellido, rol, activo, fecha_registro)
VALUES ('admin.sistema@admin.usalesiana.edu.bo', 'Admin', 'Sistema', 'ADMIN', true, CURRENT_TIMESTAMP);

-- DOCENTE
INSERT INTO login_usuarios (email, nombre, apellido, rol, activo, fecha_registro)
VALUES ('juan.perez.611ru@services.usalesiana.edu.bo', 'Juan', 'Perez', 'DOCENTE', true, CURRENT_TIMESTAMP);

-- ESTUDIANTE
INSERT INTO login_usuarios (email, nombre, apellido, rol, activo, fecha_registro)
VALUES ('maria.garcia.611ru@usalesiana.edu.bo', 'Maria', 'Garcia', 'ESTUDIANTE', true, CURRENT_TIMESTAMP);

-- Crear función para actualizar último acceso
CREATE OR REPLACE FUNCTION update_ultimo_acceso()
RETURNS TRIGGER AS $$
BEGIN
    NEW.ultimo_acceso = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear trigger para actualizar último acceso en usuarios
CREATE TRIGGER trigger_update_ultimo_acceso
BEFORE UPDATE ON login_usuarios
FOR EACH ROW
WHEN (OLD.* IS DISTINCT FROM NEW.*)
EXECUTE FUNCTION update_ultimo_acceso();

-- Crear trigger para actualizar último acceso en usersession
CREATE TRIGGER trigger_update_ultimo_acceso_session
BEFORE UPDATE ON login_usersession
FOR EACH ROW
WHEN (OLD.* IS DISTINCT FROM NEW.*)
EXECUTE FUNCTION update_ultimo_acceso();



CREATE TABLE Sesiones (
    sesion_id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    token_sesion VARCHAR(255) UNIQUE NOT NULL,
    si_activo BOOLEAN DEFAULT TRUE,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultimo_acceso TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);




-- Tabla para registrar cada correo electrónico
CREATE TABLE CorreosRegistrados (
    correo_id SERIAL PRIMARY KEY,
    correo VARCHAR(255) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla para adminroot
CREATE TABLE adminroot (
    id INT GENERATED ALWAYS AS (EXTRACT(YEAR FROM creado_en)) STORED PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
);


-- Insert sample data into the tables

-- Insert sample data into Usuarios table
INSERT INTO Usuarios (correo_univ, hash_contraseña, nombre_completo, role_id)
VALUES 
('admin@usalesiana.edu.bo', 'hashedpassword1', 'Admin User', 1),
('docente@servicios.usalesiana.edu.bo', 'hashedpassword2', 'Docente User', 2),
('estudiante@usalesiana.edu.bo', 'hashedpassword3', 'Estudiante User', 3);

-- Insert sample data into Sesiones table
INSERT INTO Sesiones (usuario_id, token_sesion)
VALUES 
(1, 'token1'),
(2, 'token2'),
(3, 'token3');

-- Insert sample data into CorreosRegistrados table
INSERT INTO CorreosRegistrados (correo)
VALUES 
('admin@usalesiana.edu.bo'),
('docente@servicios.usalesiana.edu.bo'),
('estudiante@usalesiana.edu.bo');

-- Insert sample data into adminroot table
INSERT INTO adminroot (password)
VALUES 
('root2024usb-cspg');

-- Insert sample data into Administrador table
INSERT INTO Administrador (usuario_id, departamento, permisos)
VALUES 
(1, 'IT', '{"access_level": "full"}');

-- Insert sample data into Docentes table
INSERT INTO Docentes (usuario_id, departamento, especializacion, ubicacion_oficina, numero_telefono)
VALUES 
(2, 'Computer Science', 'Software Engineering', 'Building A, Room 101', '123456789');

-- Insert sample data into Estudiantes table
INSERT INTO Estudiantes (usuario_id, numero_matricula, programa_de_grado, anio_de_estudio, id_advisor)
VALUES 
(3, '20210001', 'Computer Science', 2, 2);




-- administrador , docente , estudiante 
-- perfiles 

CREATE TABLE Administrador (
    admin_id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    departamento VARCHAR(255),
    permisos JSONB DEFAULT '{}',
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
ALTER TABLE Administrador
ADD CONSTRAINT fk_admin_usuario
FOREIGN KEY (usuario_id) REFERENCES Usuarios(usuario_id);

-- Tabla para docentes
CREATE TABLE Docentes (
    docente_id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    departamento VARCHAR(255),
    especializacion VARCHAR(255),
    ubicacion_oficina VARCHAR(255),
    numero_telefono VARCHAR(15),
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
ALTER TABLE Docentes
ADD CONSTRAINT fk_docente_usuario
FOREIGN KEY (usuario_id) REFERENCES Usuarios(usuario_id);

-- Tabla para estudiantes
CREATE TABLE Estudiantes (
    estudiante_id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    numero_matricula VARCHAR(20) UNIQUE NOT NULL,
    programa_de_grado VARCHAR(255),
    anio_de_estudio INT,
    id_advisor INT,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
ALTER TABLE Estudiantes
ADD CONSTRAINT fk_estudiante_usuario
FOREIGN KEY (usuario_id) REFERENCES Usuarios(usuario_id);

ALTER TABLE Estudiantes
ADD CONSTRAINT fk_estudiante_advisor
FOREIGN KEY (id_advisor) REFERENCES Docentes(docente_id);


CREATE TABLE Roles (
    role_id SERIAL PRIMARY KEY,
    role_nombre VARCHAR(50) NOT NULL UNIQUE
);
		
CREATE TABLE Proyectos (
    proyecto_id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    estudiante_id INT NOT NULL,
    docente_id INT NOT NULL,
    estado VARCHAR(50) NOT NULL,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Estado (
    estado_id SERIAL PRIMARY KEY,
    estado_nombre VARCHAR(100) NOT NULL UNIQUE
);


-- actividades 
CREATE TABLE Actividades (
    actividad_id SERIAL PRIMARY KEY,
    creador_id INT NOT NULL,  -- Relacionado con el usuario que crea la actividad o reunión
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT,
    fecha_asignacion DATE NOT NULL,
    limete_fecha DATE,  -- Fecha límite para la actividad (opcional)
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    actualizado_up TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- etapa base del estado 

CREATE TABLE Etapas (
    etapa_id SERIAL PRIMARY KEY,
    nombre_etapa VARCHAR(255) NOT NULL,
    descripcion TEXT,
    proyecto_id INT NOT NULL,
    completado BOOLEAN DEFAULT FALSE,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE Supervision (
    supervision_id SERIAL PRIMARY KEY,
    proyecto_id INT NOT NULL,
    docente_id INT NOT NULL,
    supervision_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);




CREATE TABLE Envios (
    envío_id SERIAL PRIMARY KEY,
    etapa_id INT NOT NULL,
    estudiante_id INT NOT NULL,
    url_archivo VARCHAR(255) NOT NULL,
    enviado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Retroalim (
    retroalim_id SERIAL PRIMARY KEY,
    envio_id INT NOT NULL,
    docente_id INT NOT NULL,
    desc_retroalim TEXT,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Notificaciones (
    notificacion_id SERIAL PRIMARY KEY,
    archivo_id INT NOT NULL,
	usuario_id INT NOT NULL,
    mensaje TEXT NOT NULL,
	
    esta_leido BOOLEAN DEFAULT FALSE,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE Autenticacion (
    auth_id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    token_autenticacion VARCHAR(255) UNIQUE NOT NULL,
    esta_activo BOOLEAN DEFAULT TRUE,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultimo_login TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Telemetria (
    telemetria_id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    accion VARCHAR(255) NOT NULL,
    descripcion TEXT,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Seguimiento (
    seguimiento_id SERIAL PRIMARY KEY,
    proyecto_id INT NOT NULL,
    estado_id INT NOT NULL,
    detalles_actualizacion TEXT,
    actualizado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Informes (
    informe_id SERIAL PRIMARY KEY,
    proyecto_id INT NOT NULL,
    generado_por INT NOT NULL,
    texto_informe TEXT,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

 
-- archivos base 
-- tabla administrador
CREATE TABLE ArchivosAdmin (
    archivo_id SERIAL PRIMARY KEY,
    admin_id INT not null,
    titulo_archivo VARCHAR(255) NOT NULL,
    descripcion_archivo TEXT,
    ruta_archivo VARCHAR(255) NOT NULL,  -- Ruta o nombre del archivo en el sistema
    notificar_todos BOOLEAN DEFAULT TRUE,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Indica si se notifica a todos los usuarios
    FOREIGN KEY (admin_id) REFERENCES Usuarios(usuario_id)
);

-- tabla docente 

CREATE TABLE ArchivosDocente (
    archivo_id SERIAL PRIMARY KEY,
    docente_id INT NOT NULL,  -- Relacionado con el usuario que sube el archivo (debe ser un administrador)
    titulo_archivo VARCHAR(255) NOT NULL,
    descripcion_archivo TEXT,
    ruta_archivo VARCHAR(255) NOT NULL,  -- Ruta o nombre del archivo en el sistema
    notificar_todos BOOLEAN DEFAULT TRUE,  -- Indica si se notifica a todos los usuarios estudiantes
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (docente_id) REFERENCES Usuarios(usuario_id)          
);

-- tabla estudiante - docente 
CREATE TABLE ArchivosEstud (
    archivo_id SERIAL PRIMARY KEY,
    estudiante_id INT NOT NULL,  -- Relacionado con el usuario que sube el archivo (debe ser un administrador)
    titulo_archivo VARCHAR(255) NOT NULL,
    descripcion_archivo TEXT,
    ruta_archivo VARCHAR(255) NOT NULL,  -- Ruta o nombre del archivo en el sistema
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (estudiante_id) REFERENCES Usuarios(usuario_id)
);







-- relaciones 
-- asignaciones 




ALTER TABLE Usuarios
ADD CONSTRAINT fk_usuario_rol
FOREIGN KEY (role_id) REFERENCES Roles(role_id);

ALTER TABLE Actividades
ADD CONSTRAINT fk_actividades_creador
FOREIGN KEY (creador_id) REFERENCES Usuarios(usuario_id);

ALTER TABLE Proyectos
ADD CONSTRAINT fk_proyecto_estudiante
FOREIGN KEY (estudiante_id) REFERENCES Usuarios(usuario_id);

ALTER TABLE Proyectos
ADD CONSTRAINT fk_proyecto_docente
FOREIGN KEY (docente_id) REFERENCES Usuarios(usuario_id);

ALTER TABLE Supervision
ADD CONSTRAINT fk_supervision_docente
FOREIGN KEY (docente_id) REFERENCES Usuarios(usuario_id);

ALTER TABLE Envios
ADD CONSTRAINT fk_entrega_estudiante
FOREIGN KEY (estudiante_id) REFERENCES Usuarios(usuario_id);

ALTER TABLE Retroalim
ADD CONSTRAINT fk_retroalimentacion_docente
FOREIGN KEY (docente_id) REFERENCES Usuarios(usuario_id);

ALTER TABLE Notificaciones
ADD CONSTRAINT fk_notificacion_usuario
FOREIGN KEY (usuario_id) REFERENCES Usuarios(usuario_id);

ALTER TABLE Autenticacion
ADD CONSTRAINT fk_autenticacion_usuario
FOREIGN KEY (usuario_id) REFERENCES Usuarios(usuario_id);

ALTER TABLE Telemetria
ADD CONSTRAINT fk_telemetria_usuario
FOREIGN KEY (usuario_id) REFERENCES Usuarios(usuario_id);

ALTER TABLE Informes
ADD CONSTRAINT fk_informe_generado_por
FOREIGN KEY (generado_por) REFERENCES Usuarios(usuario_id);

-- segmentos alternativos 
ALTER TABLE Etapas
ADD CONSTRAINT fk_etapa_proyecto
FOREIGN KEY (proyecto_id) REFERENCES Proyectos(proyecto_id);

ALTER TABLE Envios
ADD CONSTRAINT fk_entrega_etapa
FOREIGN KEY (etapa_id) REFERENCES Etapas(etapa_id);

ALTER TABLE Seguimiento
ADD CONSTRAINT fk_seguimiento_proyecto
FOREIGN KEY (proyecto_id) REFERENCES Proyectos(proyecto_id);

ALTER TABLE Seguimiento
ADD CONSTRAINT fk_seguimiento_estado
FOREIGN KEY (estado_id) REFERENCES Estado(estado_id);

ALTER TABLE Informes
ADD CONSTRAINT fk_informe_proyecto
FOREIGN KEY (proyecto_id) REFERENCES Proyectos(proyecto_id);

ALTER TABLE Supervision
ADD CONSTRAINT fk_supervision_proyecto
FOREIGN KEY (proyecto_id) REFERENCES Proyectos(proyecto_id);

ALTER TABLE Retroalim
ADD CONSTRAINT fk_retroalimentacion_entrega
FOREIGN KEY (envio_id) REFERENCES Envios(envio_id);


ALTER TABLE Sesiones
ADD CONSTRAINT fk_sesiones_usuario
FOREIGN KEY (usuario_id) REFERENCES Usuarios(usuario_id);

-- subida de archivos 

ALTER TABLE ArchivosAdmin
ADD CONSTRAINT fk_archivos_admin
FOREIGN KEY (admin_id) REFERENCES Usuarios(usuario_id);

ALTER TABLE Notificaciones
ADD CONSTRAINT fk_notificaciones_archivo
FOREIGN KEY (archivo_id) REFERENCES ArchivosAdmin(archivo_id);

ALTER TABLE Notificaciones
DROP CONSTRAINT fk_notificaciones_archivo;

ALTER TABLE Notificaciones
ADD CONSTRAINT fk_notificaciones_archivo
FOREIGN KEY (archivo_id) REFERENCES ArchivosAdmin(archivo_id) ON UPDATE CASCADE;

ALTER TABLE Notificaciones
   ADD COLUMN archivo_id INT;

ALTER TABLE Notificaciones
ADD CONSTRAINT fk_notificaciones_usuario
FOREIGN KEY (usuario_id) REFERENCES Usuarios(usuario_id);



--- Queries

-- Insertar usuarios docentes
INSERT INTO login_usuarios (email, nombre, apellido, rol, activo, fecha_registro, ultimo_acceso) VALUES
('juan.perez.611ru@services.usalesiana.edu.bo', 'Juan', 'Pérez', 'DOCENTE', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('maria.lopez.611ru@services.usalesiana.edu.bo', 'María', 'López', 'DOCENTE', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('carlos.garcia.611ru@services.usalesiana.edu.bo', 'Carlos', 'García', 'DOCENTE', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insertar usuarios estudiantes
INSERT INTO login_usuarios (email, nombre, apellido, rol, activo, fecha_registro, ultimo_acceso) VALUES
('ana.martinez.611ru@usalesiana.edu.bo', 'Ana', 'Martínez', 'ESTUDIANTE', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('pedro.sanchez.611ru@usalesiana.edu.bo', 'Pedro', 'Sánchez', 'ESTUDIANTE', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('laura.torres.611ru@usalesiana.edu.bo', 'Laura', 'Torres', 'ESTUDIANTE', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insertar proyectos
INSERT INTO proyectos (titulo, descripcion, estudiante_id, docente_id, estado, creado_en) VALUES
('Sistema de Gestión Académica', 'Desarrollo de un sistema para gestión académica universitaria', 4, 1, 'ACTIVO', CURRENT_TIMESTAMP),
('Aplicación Móvil de Biblioteca', 'App móvil para gestión de biblioteca universitaria', 5, 2, 'ACTIVO', CURRENT_TIMESTAMP),
('Portal de Servicios Estudiantiles', 'Portal web para servicios estudiantiles', 6, 3, 'ACTIVO', CURRENT_TIMESTAMP);

-- Insertar supervisiones
INSERT INTO supervision (proyecto_id, docente_id, supervision_date) VALUES
(1, 1, CURRENT_TIMESTAMP + INTERVAL '1 day'),
(2, 2, CURRENT_TIMESTAMP + INTERVAL '2 days'),
(3, 3, CURRENT_TIMESTAMP + INTERVAL '3 days'),
(1, 1, CURRENT_TIMESTAMP + INTERVAL '7 days'),
(2, 2, CURRENT_TIMESTAMP + INTERVAL '8 days'),
(3, 3, CURRENT_TIMESTAMP + INTERVAL '9 days');

-- Insertar registros de actividad
INSERT INTO activity_log (usuario_id, tipo_actividad, descripcion, fecha_registro) VALUES
(1, 'actividad', 'Revisión de proyecto', CURRENT_TIMESTAMP - INTERVAL '1 hour'),
(1, 'reunion', 'Reunión con estudiante', CURRENT_TIMESTAMP - INTERVAL '2 hours'),
(2, 'actividad', 'Revisión de documentación', CURRENT_TIMESTAMP - INTERVAL '3 hours'),
(2, 'chat', 'Chat con estudiante', CURRENT_TIMESTAMP - INTERVAL '4 hours'),
(3, 'actividad', 'Evaluación de avance', CURRENT_TIMESTAMP - INTERVAL '5 hours'),
(3, 'reunion', 'Reunión de seguimiento', CURRENT_TIMESTAMP - INTERVAL '6 hours');

-- Insertar etapas de proyectos
INSERT INTO etapas (nombre_etapa, descripcion, proyecto_id, completado, creado_en) VALUES
('Planificación', 'Fase de planificación del proyecto', 1, true, CURRENT_TIMESTAMP),
('Desarrollo', 'Fase de desarrollo del proyecto', 1, false, CURRENT_TIMESTAMP),
('Planificación', 'Fase de planificación del proyecto', 2, true, CURRENT_TIMESTAMP),
('Desarrollo', 'Fase de desarrollo del proyecto', 2, false, CURRENT_TIMESTAMP),
('Planificación', 'Fase de planificación del proyecto', 3, true, CURRENT_TIMESTAMP),
('Desarrollo', 'Fase de desarrollo del proyecto', 3, false, CURRENT_TIMESTAMP);

-- Insertar telemetría
INSERT INTO telemetria (usuario_id, accion, descripcion, creado_en) VALUES
(1, 'LOGIN', 'Inicio de sesión', CURRENT_TIMESTAMP - INTERVAL '1 day'),
(1, 'REVISION', 'Revisión de proyecto', CURRENT_TIMESTAMP - INTERVAL '23 hours'),
(2, 'LOGIN', 'Inicio de sesión', CURRENT_TIMESTAMP - INTERVAL '2 days'),
(2, 'REUNION', 'Reunión con estudiante', CURRENT_TIMESTAMP - INTERVAL '47 hours'),
(3, 'LOGIN', 'Inicio de sesión', CURRENT_TIMESTAMP - INTERVAL '3 days'),
(3, 'REVISION', 'Revisión de documentación', CURRENT_TIMESTAMP - INTERVAL '71 hours');

-- Insertar seguimiento
INSERT INTO seguimiento (proyecto_id, estado_id, detalles_actualizacion, actualizado_en) VALUES
(1, 1, 'Inicio del proyecto', CURRENT_TIMESTAMP - INTERVAL '1 week'),
(1, 2, 'Avance del 25%', CURRENT_TIMESTAMP - INTERVAL '6 days'),
(2, 1, 'Inicio del proyecto', CURRENT_TIMESTAMP - INTERVAL '2 weeks'),
(2, 2, 'Avance del 15%', CURRENT_TIMESTAMP - INTERVAL '13 days'),
(3, 1, 'Inicio del proyecto', CURRENT_TIMESTAMP - INTERVAL '3 weeks'),
(3, 2, 'Avance del 30%', CURRENT_TIMESTAMP - INTERVAL '20 days');

-- Insertar estados
INSERT INTO estado (estado_nombre) VALUES
('Iniciado'),
('En Progreso'),
('En Revisión'),
('Completado'),
('Suspendido');

-- Insertar roles si no existen
INSERT INTO roles (role_nombre) VALUES
('ESTUDIANTE'),
('DOCENTE'),
('ADMIN'),
('ROOT_ADMIN');

-- Insertar datos para proyectos
INSERT INTO proyectos (titulo, descripcion, estudiante_id, docente_id, estado, creado_en) VALUES
('Sistema de Gestión Hospitalaria', 'Desarrollo de un sistema integral para la gestión hospitalaria', 4, 3, 'ACTIVO', CURRENT_TIMESTAMP),
('App Móvil de Seguimiento Académico', 'Aplicación para seguimiento de rendimiento académico', 5, 3, 'ACTIVO', CURRENT_TIMESTAMP),
('Portal de Servicios Estudiantiles', 'Desarrollo de portal web para servicios universitarios', 6, 3, 'EN_REVISION', CURRENT_TIMESTAMP),
('Sistema de Control de Biblioteca', 'Sistema para gestión y control de biblioteca', 7, 3, 'ACTIVO', CURRENT_TIMESTAMP);

-- Insertar datos para etapas de los proyectos
INSERT INTO etapas (nombre_etapa, descripcion, proyecto_id, completado, creado_en) VALUES
('Planificación', 'Fase inicial de planificación', 1, true, CURRENT_TIMESTAMP),
('Análisis', 'Análisis de requerimientos', 1, true, CURRENT_TIMESTAMP),
('Desarrollo', 'Fase de desarrollo', 1, false, CURRENT_TIMESTAMP),
('Planificación', 'Fase inicial de planificación', 2, true, CURRENT_TIMESTAMP),
('Análisis', 'Análisis de requerimientos', 2, true, CURRENT_TIMESTAMP),
('Desarrollo', 'Fase de desarrollo', 2, true, CURRENT_TIMESTAMP),
('Testing', 'Fase de pruebas', 2, false, CURRENT_TIMESTAMP);

-- Insertar datos para supervisiones
INSERT INTO supervision (proyecto_id, docente_id, supervision_date) VALUES
(1, 3, CURRENT_TIMESTAMP + INTERVAL '2 days'),
(2, 3, CURRENT_TIMESTAMP + INTERVAL '3 days'),
(3, 3, CURRENT_TIMESTAMP + INTERVAL '5 days'),
(4, 3, CURRENT_TIMESTAMP + INTERVAL '7 days');

-- Insertar datos de actividad
INSERT INTO activity_log (usuario_id, tipo_actividad, descripcion, fecha_registro) VALUES
(3, 'REVISION', 'Revisión de documentación del proyecto 1', CURRENT_TIMESTAMP - INTERVAL '2 hours'),
(3, 'REUNION', 'Reunión con estudiante del proyecto 2', CURRENT_TIMESTAMP - INTERVAL '1 day'),
(3, 'CHAT', 'Sesión de chat con estudiante del proyecto 3', CURRENT_TIMESTAMP - INTERVAL '4 hours'),
(3, 'REVISION', 'Revisión de avance del proyecto 4', CURRENT_TIMESTAMP - INTERVAL '3 hours');

-- Insertar datos de telemetría
INSERT INTO telemetria (usuario_id, accion, descripcion, creado_en) VALUES
(3, 'LOGIN', 'Inicio de sesión', CURRENT_TIMESTAMP - INTERVAL '8 hours'),
(3, 'REVISION_PROYECTO', 'Revisión de documentos', CURRENT_TIMESTAMP - INTERVAL '6 hours'),
(3, 'REUNION_VIRTUAL', 'Reunión virtual con estudiante', CURRENT_TIMESTAMP - INTERVAL '4 hours'),
(3, 'CALIFICACION', 'Calificación de entregable', CURRENT_TIMESTAMP - INTERVAL '2 hours');

-- Insertar datos de seguimiento
INSERT INTO seguimiento (proyecto_id, estado_id, detalles_actualizacion, actualizado_en) VALUES
(1, 2, 'Avance del 45% en el desarrollo', CURRENT_TIMESTAMP - INTERVAL '2 days'),
(2, 3, 'En fase de pruebas', CURRENT_TIMESTAMP - INTERVAL '1 day'),
(3, 1, 'Iniciando fase de análisis', CURRENT_TIMESTAMP - INTERVAL '3 days'),
(4, 2, 'Desarrollo en curso', CURRENT_TIMESTAMP - INTERVAL '4 days');

-- Insertar datos de archivos docente
INSERT INTO archivos_docente (docente_id, titulo_archivo, descripcion_archivo, ruta_archivo, notificar_todos, creado_en) VALUES
(3, 'Guía de Desarrollo', 'Guía metodológica para el desarrollo del proyecto', '/archivos/guia_desarrollo.pdf', true, CURRENT_TIMESTAMP),
(3, 'Plantilla de Documentación', 'Plantilla para documentación técnica', '/archivos/plantilla_doc.docx', true, CURRENT_TIMESTAMP),
(3, 'Rúbrica de Evaluación', 'Criterios de evaluación del proyecto', '/archivos/rubrica.pdf', true, CURRENT_TIMESTAMP);

-- Insertar datos de archivos estudiante
INSERT INTO archivos_estud (estudiante_id, titulo_archivo, descripcion_archivo, ruta_archivo, creado_en) VALUES
(4, 'Entrega Parcial 1', 'Primera entrega del proyecto', '/archivos/entrega1_est1.pdf', CURRENT_TIMESTAMP),
(5, 'Avance Desarrollo', 'Avance del desarrollo del sistema', '/archivos/avance_est2.pdf', CURRENT_TIMESTAMP),
(6, 'Documentación Técnica', 'Documentación del sistema', '/archivos/doc_est3.pdf', CURRENT_TIMESTAMP);

-- Insertar datos de notificaciones
INSERT INTO notificaciones (archivo_id, usuario_id, mensaje, esta_leido, creado_en) VALUES
(1, 3, 'Nueva entrega del estudiante Juan Pérez', false, CURRENT_TIMESTAMP),
(2, 3, 'Comentarios pendientes de revisión', false, CURRENT_TIMESTAMP),
(3, 3, 'Reunión programada para mañana', false, CURRENT_TIMESTAMP); 