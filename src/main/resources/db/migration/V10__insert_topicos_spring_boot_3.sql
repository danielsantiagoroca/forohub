-- 5 tópicos sobre Spring Boot 3 (curso creado en V6, autores creados en V9)
INSERT INTO topico (titulo, mensaje, status, autor_id, curso_id) VALUES
    (
        '¿Cómo migrar un proyecto de Spring Boot 2 a Spring Boot 3?',
        'Tengo una app con Spring Boot 2.7 y quiero actualizarla a la versión 3. ¿Cuáles son los cambios más importantes y qué dependencias debo revisar primero?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Martín Sosa'),
        (SELECT id FROM curso WHERE nombre = 'Spring Boot 3')
    ),
    (
        'Configuración de seguridad con Spring Security 6 en Spring Boot 3',
        'En Spring Boot 3 la clase WebSecurityConfigurerAdapter fue eliminada. ¿Cómo configuro correctamente el SecurityFilterChain y qué cambia respecto a la versión anterior?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Valentina Cruz'),
        (SELECT id FROM curso WHERE nombre = 'Spring Boot 3')
    ),
    (
        'JWT con Spring Boot 3: ¿qué librería recomiendan?',
        'Estoy evaluando jjwt vs nimbus-jose-jwt para manejar tokens JWT en Spring Boot 3. ¿Cuál tiene mejor soporte y documentación actualizada?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Diego Herrera'),
        (SELECT id FROM curso WHERE nombre = 'Spring Boot 3')
    ),
    (
        'Spring Data JPA con Jakarta EE en Spring Boot 3',
        'Noté que en Spring Boot 3 los imports cambiaron de javax.* a jakarta.*. ¿Hay algo más que tener en cuenta al trabajar con JPA e Hibernate en esta versión?',
        'CERRADO',
        (SELECT id FROM autor WHERE nombre = 'Camila Romero'),
        (SELECT id FROM curso WHERE nombre = 'Spring Boot 3')
    ),
    (
        'Flyway con Spring Boot 3: ¿cómo organizar las migraciones?',
        '¿Cuál es la mejor práctica para versionar las migraciones SQL con Flyway en un proyecto Spring Boot 3? ¿Cómo manejo los entornos de desarrollo y producción?',
        'ABIERTO',
        (SELECT id FROM autor WHERE nombre = 'Santiago López'),
        (SELECT id FROM curso WHERE nombre = 'Spring Boot 3')
    );
