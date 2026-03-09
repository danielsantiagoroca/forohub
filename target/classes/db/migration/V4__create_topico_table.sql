-- Tópicos del foro: título, mensaje, estado, autor, curso
CREATE TABLE topico (
    id             BIGSERIAL PRIMARY KEY,
    titulo         VARCHAR(255) NOT NULL,
    mensaje        TEXT NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status         VARCHAR(50) NOT NULL DEFAULT 'ABIERTO',
    autor_id       BIGINT NOT NULL REFERENCES autor (id),
    curso_id       BIGINT NOT NULL REFERENCES curso (id),
    UNIQUE (titulo, mensaje)
);

CREATE INDEX idx_topico_curso ON topico (curso_id);
CREATE INDEX idx_topico_autor ON topico (autor_id);
