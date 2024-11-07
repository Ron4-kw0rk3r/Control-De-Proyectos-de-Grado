-- Consulta para ver las tablas existentes
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'public';

-- Consulta para ver la estructura de una tabla específica
SELECT column_name, data_type, character_maximum_length, is_nullable
FROM information_schema.columns 
WHERE table_schema = 'public' 
AND table_name = 'nombre_de_la_tabla';