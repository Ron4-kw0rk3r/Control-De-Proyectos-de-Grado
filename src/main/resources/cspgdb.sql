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
