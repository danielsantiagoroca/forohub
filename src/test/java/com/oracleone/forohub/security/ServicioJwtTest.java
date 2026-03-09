package com.oracleone.forohub.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("ServicioJwt")
class ServicioJwtTest {

    @Autowired
    private ServicioJwt servicioJwt;

    @Test
    @DisplayName("genera un token no nulo para un email")
    void generaToken() {
        String token = servicioJwt.generarToken("test@alura.com");
        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    @DisplayName("extrae el email del token generado")
    void extraeEmail() {
        String email = "usuario@test.com";
        String token = servicioJwt.generarToken(email);
        assertEquals(email, servicioJwt.extraerEmail(token));
    }

    @Test
    @DisplayName("considera válido un token recién generado")
    void tokenRecienGeneradoEsValido() {
        String token = servicioJwt.generarToken("valid@test.com");
        assertTrue(servicioJwt.esTokenValido(token));
    }

    @Test
    @DisplayName("considera inválido un token malformado o vacío")
    void tokenInvalido() {
        assertFalse(servicioJwt.esTokenValido(""));
        assertFalse(servicioJwt.esTokenValido("token.invalido"));
    }
}

