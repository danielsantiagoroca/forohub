package com.oracleone.forohub.controller;

import com.oracleone.forohub.dto.SolicitudLogin;
import com.oracleone.forohub.dto.RespuestaLogin;
import com.oracleone.forohub.model.Usuario;
import com.oracleone.forohub.repository.UsuarioRepository;
import com.oracleone.forohub.security.ServicioJwt;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LoginController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ServicioJwt servicioJwt;

    public LoginController(UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder,
                           ServicioJwt servicioJwt) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.servicioJwt = servicioJwt;
    }

    @PostMapping("/login")
    public ResponseEntity<RespuestaLogin> login(@Valid @RequestBody SolicitudLogin request) {
        return usuarioRepository.findByEmail(request.getEmail())
                .filter(usuario -> passwordEncoder.matches(request.getPassword(), usuario.getPassword()))
                .map(Usuario::getEmail)
                .map(email -> new RespuestaLogin(servicioJwt.generarToken(email), "Bearer"))
                .map(ResponseEntity::<RespuestaLogin>ok)
                .orElse(ResponseEntity.status(401).<RespuestaLogin>build());
    }
}

