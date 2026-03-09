-- Actualiza el hash del usuario demo con uno generado correctamente para la contraseña "password"
UPDATE usuario
SET password = '$2b$10$xEdNsAKq4NeDTG6FJ/tyWeKLYXBPwuqsRXrNZH13Ffm6Bd2dv0aXG'
WHERE email = 'admin@forohub.local';
