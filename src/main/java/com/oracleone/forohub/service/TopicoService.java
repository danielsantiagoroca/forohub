package com.oracleone.forohub.service;

import com.oracleone.forohub.dto.TopicoRequest;
import com.oracleone.forohub.dto.TopicoResponse;
import com.oracleone.forohub.model.Autor;
import com.oracleone.forohub.model.Curso;
import com.oracleone.forohub.model.Topico;
import com.oracleone.forohub.repository.AutorRepository;
import com.oracleone.forohub.repository.CursoRepository;
import com.oracleone.forohub.repository.TopicoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final AutorRepository autorRepository;
    private final CursoRepository cursoRepository;

    public TopicoService(TopicoRepository topicoRepository,
                         AutorRepository autorRepository,
                         CursoRepository cursoRepository) {
        this.topicoRepository = topicoRepository;
        this.autorRepository = autorRepository;
        this.cursoRepository = cursoRepository;
    }

    @Transactional(readOnly = true)
    public List<TopicoResponse> listarTodos() {
        return topicoRepository.findAll().stream()
                .map(TopicoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TopicoResponse obtenerPorId(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado con id: " + id));
        return TopicoResponse.fromEntity(topico);
    }

    @Transactional
    public TopicoResponse crear(TopicoRequest request) {
        if (topicoRepository.existsByTituloAndMensaje(request.getTitulo(), request.getMensaje())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un tópico con el mismo título y mensaje");
        }
        Autor autor = autorRepository.findById(request.getAutorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado"));
        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));

        Topico topico = new Topico();
        topico.setTitulo(request.getTitulo());
        topico.setMensaje(request.getMensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);
        topico.setStatus(request.getStatus() != null ? request.getStatus() : "ABIERTO");

        return TopicoResponse.fromEntity(topicoRepository.save(topico));
    }

    @Transactional
    public TopicoResponse actualizar(Long id, TopicoRequest request) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado con id: " + id));

        if (topicoRepository.existsByTituloAndMensaje(request.getTitulo(), request.getMensaje())
                && (!topico.getTitulo().equals(request.getTitulo()) || !topico.getMensaje().equals(request.getMensaje()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe otro tópico con el mismo título y mensaje");
        }

        Autor autor = autorRepository.findById(request.getAutorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado"));
        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));

        topico.setTitulo(request.getTitulo());
        topico.setMensaje(request.getMensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);
        topico.setStatus(request.getStatus() != null ? request.getStatus() : "ABIERTO");

        return TopicoResponse.fromEntity(topicoRepository.save(topico));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado con id: " + id);
        }
        topicoRepository.deleteById(id);
    }
}
