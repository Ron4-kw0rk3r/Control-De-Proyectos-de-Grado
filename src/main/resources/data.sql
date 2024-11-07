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