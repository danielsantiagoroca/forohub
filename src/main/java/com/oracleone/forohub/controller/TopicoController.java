package com.oracleone.forohub.controller;

import com.oracleone.forohub.dto.TopicoRequest;
import com.oracleone.forohub.dto.TopicoResponse;
import com.oracleone.forohub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService topicoService;

    public TopicoController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    @GetMapping
    public ResponseEntity<List<TopicoResponse>> listar() {
        return ResponseEntity.ok(topicoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(topicoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<TopicoResponse> crear(@Valid @RequestBody TopicoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(topicoService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponse> actualizar(@PathVariable Long id,
                                                     @Valid @RequestBody TopicoRequest request) {
        return ResponseEntity.ok(topicoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        topicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
