package com.example.inicial1.security.controller;

import com.example.inicial1.entities.Usuario;
import com.example.inicial1.entities.RefreshToken;
import com.example.inicial1.repositories.UsuarioRepository;
import com.example.inicial1.security.dto.JwtDto;
import com.example.inicial1.security.dto.LoginUsuario;
import com.example.inicial1.security.jwt.JwtProvider;
import com.example.inicial1.services.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin // Crucial para conectar con Angular
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    // Inyectamos las nuevas dependencias
    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario) {
        System.out.println(">>> [DEBUG] Frontend envió - Usuario: " + loginUsuario.getUsername());

        try {
            // 1. Autenticamos al usuario normalmente
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUsuario.getUsername(), loginUsuario.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 2. Generamos el Access Token de vida corta
            String jwt = jwtProvider.generateToken(authentication);

            // 3. Buscamos al usuario para generarle el Refresh Token de vida larga
            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(loginUsuario.getUsername());
            if (usuarioOpt.isEmpty()) {
                throw new RuntimeException("Usuario no encontrado en la base de datos");
            }

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(usuarioOpt.get().getId());

            System.out.println(">>> [DEBUG] ¡Login Exitoso! Tokens generados.");

            // 4. Devolvemos el DTO actualizado con AMBOS tokens
            return ResponseEntity.ok(new JwtDto(jwt, "Bearer", loginUsuario.getUsername(), refreshToken.getToken()));

        } catch (Exception e) {
            System.out.println(">>> [ERROR DE LOGIN] Motivo exacto: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Nuevo Endpoint: Recibe un Refresh Token, verifica si es válido,
     * y devuelve un Access Token fresquito.
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String requestRefreshToken = request.get("refreshToken");

        try {
            return refreshTokenService.findByToken(requestRefreshToken)
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUsuario)
                    .map(usuario -> {
                        // Si todo está bien, generamos el nuevo Access Token
                        String nuevoAccessToken = jwtProvider.generateTokenFromUsername(usuario.getUsername());

                        // Retornamos los datos. Mantenemos el mismo refresh token.
                        return ResponseEntity.ok(new JwtDto(nuevoAccessToken, "Bearer", usuario.getUsername(), requestRefreshToken));
                    })
                    .orElseThrow(() -> new RuntimeException("Refresh token no encontrado en la base de datos."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}