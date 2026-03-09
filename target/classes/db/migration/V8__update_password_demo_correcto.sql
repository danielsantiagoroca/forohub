-- Corrige el hash del usuario demo con el valor BCrypt válido para la contraseña "password"
UPDATE usuario
SET password = '$2b$14$Agpb8mrI87HedQabpHFuuexXup622mRZYxw7b/Iqy9Qb8hCpV209K'
WHERE email = 'admin@forohub.local';
