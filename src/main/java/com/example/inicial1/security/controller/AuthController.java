package com.example.inicial1.security.controller;

import com.example.inicial1.entities.Usuario;
import com.example.inicial1.entities.TipoUsuario;
import com.example.inicial1.security.dto.JwtDto;
import com.example.inicial1.security.dto.LoginUsuario;
import com.example.inicial1.security.dto.NuevoUsuario;
import com.example.inicial1.security.jwt.JwtProvider;
import com.example.inicial1.services.UsuarioServiceImpl; // Asumiendo que tienes un servicio para el ABM
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin // Crucial para conectar con Angular
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario) {
        // 1. Vemos qué está recibiendo de Angular
        System.out.println(">>> [DEBUG] Frontend envió - Usuario: " + loginUsuario.getUsername() + " | Password: " + loginUsuario.getPassword());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUsuario.getUsername(), loginUsuario.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);

            System.out.println(">>> [DEBUG] ¡Login Exitoso! Token generado.");
            return ResponseEntity.ok(new JwtDto(jwt, "Bearer", loginUsuario.getUsername()));

        } catch (Exception e) {
            // 2. Si falla, que nos diga exactamente POR QUÉ (Bad credentials, User disabled, etc.)
            System.out.println(">>> [ERROR DE LOGIN] Motivo exacto: " + e.getMessage());
            throw e; // Dejamos que devuelva el 401
        }
    }

    // Aquí podrías añadir el método /nuevo para registrar clientes (CU N°3 del relevamiento)
}