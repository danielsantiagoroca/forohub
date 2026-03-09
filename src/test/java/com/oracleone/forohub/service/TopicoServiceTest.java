package com.oracleone.forohub.service;

import com.oracleone.forohub.dto.TopicoRequest;
import com.oracleone.forohub.dto.TopicoResponse;
import com.oracleone.forohub.model.Autor;
import com.oracleone.forohub.model.Curso;
import com.oracleone.forohub.model.Topico;
import com.oracleone.forohub.repository.AutorRepository;
import com.oracleone.forohub.repository.CursoRepository;
import com.oracleone.forohub.repository.TopicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TopicoService")
class TopicoServiceTest {

    @Mock
    private TopicoRepository topicoRepository;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private TopicoService topicoService;

    private Autor autor;
    private Curso curso;
    private Topico topico;
    private TopicoRequest request;

    @BeforeEach
    void setUp() {
        autor = new Autor(1L, "Autor Test");
        curso = new Curso(1L, "Curso Test");
        topico = new Topico();
        topico.setId(1L);
        topico.setTitulo("Título");
        topico.setMensaje("Mensaje");
        topico.setStatus("ABIERTO");
        topico.setAutor(autor);
        topico.setCurso(curso);

        request = new TopicoRequest();
        request.setTitulo("Título");
        request.setMensaje("Mensaje");
        request.setAutorId(1L);
        request.setCursoId(1L);
        request.setStatus("ABIERTO");
    }

    @Nested
    @DisplayName("listarTodos")
    class ListarTodos {

        @Test
        @DisplayName("devuelve lista vacía si no hay tópicos")
        void listaVacia() {
            when(topicoRepository.findAll()).thenReturn(List.of());
            List<TopicoResponse> result = topicoService.listarTodos();
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("devuelve lista de tópicos convertidos a response")
        void listaConTopicos() {
            when(topicoRepository.findAll()).thenReturn(List.of(topico));
            List<TopicoResponse> result = topicoService.listarTodos();
            assertEquals(1, result.size());
            assertEquals("Título", result.get(0).getTitulo());
            assertEquals("Autor Test", result.get(0).getAutorNombre());
            assertEquals("Curso Test", result.get(0).getCursoNombre());
        }
    }

    @Nested
    @DisplayName("obtenerPorId")
    class ObtenerPorId {

        @Test
        @DisplayName("devuelve tópico cuando existe")
        void existe() {
            when(topicoRepository.findById(1L)).thenReturn(Optional.of(topico));
            TopicoResponse result = topicoService.obtenerPorId(1L);
            assertEquals(1L, result.getId());
            assertEquals("Título", result.getTitulo());
        }

        @Test
        @DisplayName("lanza 404 cuando no existe")
        void noExiste() {
            when(topicoRepository.findById(99L)).thenReturn(Optional.empty());
            assertThrows(ResponseStatusException.class, () -> topicoService.obtenerPorId(99L));
        }
    }

    @Nested
    @DisplayName("crear")
    class Crear {

        @Test
        @DisplayName("crea tópico cuando datos válidos y no duplicado")
        void ok() {
            when(topicoRepository.existsByTituloAndMensaje("Título", "Mensaje")).thenReturn(false);
            when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
            when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
            when(topicoRepository.save(any(Topico.class))).thenAnswer(inv -> {
                Topico t = inv.getArgument(0);
                t.setId(1L);
                return t;
            });

            TopicoResponse result = topicoService.crear(request);
            assertNotNull(result);
            assertEquals("Título", result.getTitulo());
            verify(topicoRepository).save(any(Topico.class));
        }

        @Test
        @DisplayName("lanza 409 cuando ya existe mismo título y mensaje")
        void conflicto() {
            when(topicoRepository.existsByTituloAndMensaje("Título", "Mensaje")).thenReturn(true);
            assertThrows(ResponseStatusException.class, () -> topicoService.crear(request));
            verify(topicoRepository, never()).save(any());
        }

        @Test
        @DisplayName("lanza 404 cuando autor no existe")
        void autorNoExiste() {
            when(topicoRepository.existsByTituloAndMensaje("Título", "Mensaje")).thenReturn(false);
            when(autorRepository.findById(1L)).thenReturn(Optional.empty());
            assertThrows(ResponseStatusException.class, () -> topicoService.crear(request));
        }

        @Test
        @DisplayName("lanza 404 cuando curso no existe")
        void cursoNoExiste() {
            when(topicoRepository.existsByTituloAndMensaje("Título", "Mensaje")).thenReturn(false);
            when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
            when(cursoRepository.findById(1L)).thenReturn(Optional.empty());
            assertThrows(ResponseStatusException.class, () -> topicoService.crear(request));
        }
    }

    @Nested
    @DisplayName("actualizar")
    class Actualizar {

        @Test
        @DisplayName("actualiza cuando existe y datos válidos")
        void ok() {
            when(topicoRepository.findById(1L)).thenReturn(Optional.of(topico));
            when(topicoRepository.existsByTituloAndMensaje("Título", "Mensaje")).thenReturn(true);
            when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
            when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
            when(topicoRepository.save(any(Topico.class))).thenReturn(topico);

            TopicoResponse result = topicoService.actualizar(1L, request);
            assertNotNull(result);
            verify(topicoRepository).save(topico);
        }

        @Test
        @DisplayName("lanza 404 cuando el tópico no existe")
        void noExiste() {
            when(topicoRepository.findById(99L)).thenReturn(Optional.empty());
            assertThrows(ResponseStatusException.class, () -> topicoService.actualizar(99L, request));
        }
    }

    @Nested
    @DisplayName("eliminar")
    class Eliminar {

        @Test
        @DisplayName("elimina cuando existe")
        void ok() {
            when(topicoRepository.existsById(1L)).thenReturn(true);
            topicoService.eliminar(1L);
            verify(topicoRepository).deleteById(1L);
        }

        @Test
        @DisplayName("lanza 404 cuando no existe")
        void noExiste() {
            when(topicoRepository.existsById(99L)).thenReturn(false);
            assertThrows(ResponseStatusException.class, () -> topicoService.eliminar(99L));
            verify(topicoRepository, never()).deleteById(any());
        }
    }
}
