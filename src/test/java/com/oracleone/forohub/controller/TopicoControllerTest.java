package com.oracleone.forohub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracleone.forohub.dto.TopicoRequest;
import com.oracleone.forohub.dto.TopicoResponse;
import com.oracleone.forohub.service.TopicoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TopicoController.class)
@DisplayName("TopicoController")
class TopicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TopicoService topicoService;

    @Test
    @WithMockUser
    @DisplayName("GET /topicos devuelve 200 y lista")
    void listar() throws Exception {
        when(topicoService.listarTodos()).thenReturn(List.of(
                new TopicoResponse(1L, "T1", "M1", LocalDateTime.now(), "ABIERTO", "Autor", "Curso")
        ));
        mockMvc.perform(get("/topicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].titulo").value("T1"));
    }

    @Test
    @WithMockUser
    @DisplayName("GET /topicos/{id} devuelve 200 y tópico")
    void obtenerPorId() throws Exception {
        TopicoResponse response = new TopicoResponse(1L, "T1", "M1", LocalDateTime.now(), "ABIERTO", "Autor", "Curso");
        when(topicoService.obtenerPorId(1L)).thenReturn(response);
        mockMvc.perform(get("/topicos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("T1"));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /topicos devuelve 201 y body")
    void crear() throws Exception {
        TopicoRequest request = new TopicoRequest("Título", "Mensaje", 1L, 1L, "ABIERTO");
        TopicoResponse response = new TopicoResponse(1L, "Título", "Mensaje", LocalDateTime.now(), "ABIERTO", "Autor", "Curso");
        when(topicoService.crear(any(TopicoRequest.class))).thenReturn(response);

        mockMvc.perform(post("/topicos")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Título"));
    }

    @Test
    @WithMockUser
    @DisplayName("PUT /topicos/{id} devuelve 200")
    void actualizar() throws Exception {
        TopicoRequest request = new TopicoRequest("Título", "Mensaje", 1L, 1L, "CERRADO");
        TopicoResponse response = new TopicoResponse(1L, "Título", "Mensaje", LocalDateTime.now(), "CERRADO", "Autor", "Curso");
        when(topicoService.actualizar(eq(1L), any(TopicoRequest.class))).thenReturn(response);

        mockMvc.perform(put("/topicos/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("DELETE /topicos/{id} devuelve 204")
    void eliminar() throws Exception {
        doNothing().when(topicoService).eliminar(1L);
        mockMvc.perform(delete("/topicos/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /topicos sin autenticación devuelve 401")
    void listarSinAuth() throws Exception {
        mockMvc.perform(get("/topicos"))
                .andExpect(status().isUnauthorized());
    }
}
