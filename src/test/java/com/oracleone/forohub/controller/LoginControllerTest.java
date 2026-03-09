package com.oracleone.forohub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracleone.forohub.dto.SolicitudLogin;
import com.oracleone.forohub.model.Usuario;
import com.oracleone.forohub.repository.UsuarioRepository;
import com.oracleone.forohub.security.ServicioJwt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@DisplayName("LoginController")
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private ServicioJwt servicioJwt;

    @Test
    @DisplayName("POST /login con credenciales válidas devuelve 200 y token")
    void loginOk() throws Exception {
        String email = "admin@forohub.local";
        String rawPassword = "password";
        String hashedPassword = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";

        Usuario usuario = new Usuario(1L, "Admin Demo", email, hashedPassword);

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(rawPassword, hashedPassword)).thenReturn(true);
        when(servicioJwt.generarToken(anyString())).thenReturn("jwt-token-demo");

        SolicitudLogin request = new SolicitudLogin(email, rawPassword);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token-demo"))
                .andExpect(jsonPath("$.type").value("Bearer"));
    }
}

