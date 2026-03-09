-- Cursos del foro (ej. "Spring Boot", "Java")
CREATE TABLE curso (
    id   BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL UNIQUE
);
