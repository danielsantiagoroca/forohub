-- Cursos: cocina, música y viajes
INSERT INTO curso (nombre) VALUES
    ('Cocina Internacional'),
    ('Repostería y Pastelería'),
    ('Cocina Vegana'),
    ('Teoría Musical'),
    ('Producción Musical Digital'),
    ('Guitarra para Principiantes'),
    ('Viajes por Europa'),
    ('Fotografía de Viaje'),
    ('Turismo Sustentable');

-- Autores de prueba
INSERT INTO autor (nombre) VALUES
    ('Lucía Fernández'),
    ('Martín Sosa'),
    ('Valentina Cruz'),
    ('Diego Herrera'),
    ('Camila Romero'),
    ('Santiago López');

-- Tópicos de cocina (autor_id y curso_id referencian los ids recién insertados;
-- se usan subconsultas para no depender de ids fijos)
INSERT INTO topico (titulo, mensaje, status, autor_id, curso_id) VALUES
    (
        '¿Cómo lograr una pasta al dente perfecta?',
        'Siempre me queda o muy blanda o muy dura. ¿Cuántos minutos exactos y con cuánta sal?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Lucía Fernández'),
        (SELECT id FROM curso WHERE nombre = 'Cocina Internacional')
    ),
    (
        'Sustitutos del huevo en repostería',
        '¿Qué puedo usar en lugar de huevo para una torta esponjosa? Necesito opciones veganas.',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Valentina Cruz'),
        (SELECT id FROM curso WHERE nombre = 'Repostería y Pastelería')
    ),
    (
        'Mi bizcochuelo siempre se hunde en el centro',
        'Sigo la receta al pie de la letra pero el centro siempre queda crudo. ¿Qué estoy haciendo mal?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Camila Romero'),
        (SELECT id FROM curso WHERE nombre = 'Repostería y Pastelería')
    ),
    (
        '¿Proteína vegana en platos principales?',
        'Busco ideas para reemplazar la carne con proteína vegetal que quede bien sabrosa.',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Martín Sosa'),
        (SELECT id FROM curso WHERE nombre = 'Cocina Vegana')
    ),
    (
        'Técnica para hacer sushi en casa',
        '¿Cómo compacto bien el arroz y qué tipo de arroz es el indicado para sushi?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Diego Herrera'),
        (SELECT id FROM curso WHERE nombre = 'Cocina Internacional')
    ),
    (
        'Fermentación de kombucha: primeros pasos',
        'Quiero empezar a fermentar kombucha en casa. ¿Qué necesito y cuánto tarda?',
        'CERRADO',
        (SELECT id FROM autor WHERE nombre = 'Lucía Fernández'),
        (SELECT id FROM curso WHERE nombre = 'Cocina Vegana')
    ),

-- Tópicos de música
    (
        '¿Cómo leer partituras si soy principiante?',
        'No tengo experiencia con lectura musical. ¿Por dónde empiezo y qué recursos recomiendan?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Santiago López'),
        (SELECT id FROM curso WHERE nombre = 'Teoría Musical')
    ),
    (
        'Diferencia entre escala mayor y menor',
        'Entiendo la teoría pero no noto la diferencia al escuchar. ¿Cómo entrenar el oído?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Camila Romero'),
        (SELECT id FROM curso WHERE nombre = 'Teoría Musical')
    ),
    (
        '¿Qué DAW recomiendan para empezar a producir?',
        'Estoy entre FL Studio y Ableton. ¿Cuál tiene mejor curva de aprendizaje para principiantes?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Martín Sosa'),
        (SELECT id FROM curso WHERE nombre = 'Producción Musical Digital')
    ),
    (
        'Cómo mezclar bajos sin que tape todo lo demás',
        'Mis mezclas siempre quedan con el bajo muy dominante. ¿Tips de EQ y compresión?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Diego Herrera'),
        (SELECT id FROM curso WHERE nombre = 'Producción Musical Digital')
    ),
    (
        'Acordes básicos para empezar en guitarra',
        '¿Cuáles son los primeros acordes que debo aprender y en qué orden?',
        'CERRADO',
        (SELECT id FROM autor WHERE nombre = 'Valentina Cruz'),
        (SELECT id FROM curso WHERE nombre = 'Guitarra para Principiantes')
    ),
    (
        'Me duelen los dedos al tocar guitarra, ¿es normal?',
        'Llevo dos semanas practicando y las yemas me duelen mucho. ¿Cuándo se forman los callos?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Santiago López'),
        (SELECT id FROM curso WHERE nombre = 'Guitarra para Principiantes')
    ),

-- Tópicos de viajes
    (
        'Itinerario de 10 días por Italia con poco presupuesto',
        'Quiero visitar Roma, Florencia y Venecia. ¿Cómo organizo el transporte interno y el alojamiento?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Lucía Fernández'),
        (SELECT id FROM curso WHERE nombre = 'Viajes por Europa')
    ),
    (
        '¿Cuál es la mejor época para viajar a Portugal?',
        'Estoy pensando en Lisboa y Oporto. ¿Primavera o otoño? ¿Cómo está el clima?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Camila Romero'),
        (SELECT id FROM curso WHERE nombre = 'Viajes por Europa')
    ),
    (
        'Cómo fotografiar paisajes con poca luz',
        '¿Qué configuración de cámara uso al amanecer o atardecer para no perder detalle?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Diego Herrera'),
        (SELECT id FROM curso WHERE nombre = 'Fotografía de Viaje')
    ),
    (
        'Mejores lentes para fotografía de calle en viaje',
        'Viajo liviano y quiero un solo lente versátil para ciudad y paisaje. ¿Cuál me recomiendan?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Martín Sosa'),
        (SELECT id FROM curso WHERE nombre = 'Fotografía de Viaje')
    ),
    (
        'Consejos para viajar de forma responsable con el medioambiente',
        '¿Cómo compensar la huella de carbono de los vuelos y qué prácticas adoptar en destino?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Valentina Cruz'),
        (SELECT id FROM curso WHERE nombre = 'Turismo Sustentable')
    ),
    (
        'Alojamientos alternativos al hotel tradicional',
        '¿Eco-lodges, glamping, couchsurfing? ¿Cuáles recomiendan para una experiencia más auténtica?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Santiago López'),
        (SELECT id FROM curso WHERE nombre = 'Turismo Sustentable')
    ),
    (
        'Cómo preparar una mochila para un viaje de 2 semanas',
        'Necesito viajar solo con equipaje de mano. ¿Qué llevo y qué dejo en casa?',
        'CERRADO',
        (SELECT id FROM autor WHERE nombre = 'Lucía Fernández'),
        (SELECT id FROM curso WHERE nombre = 'Viajes por Europa')
    ),
    (
        'Apps imprescindibles para viajar solo por Europa',
        '¿Qué aplicaciones usan para transporte, alojamiento y traducción cuando viajan sin guía?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Diego Herrera'),
        (SELECT id FROM curso WHERE nombre = 'Viajes por Europa')
    );
