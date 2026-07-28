package com.example.inicial1.services;

import com.example.inicial1.entities.RefreshToken;
import com.example.inicial1.entities.Usuario;
import com.example.inicial1.repositories.RefreshTokenRepository;
import com.example.inicial1.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    // Tiempo de vida del Refresh Token (Ejemplo: 12 horas para cubrir un turno completo)
    private static final long REFRESH_TOKEN_EXPIRATION_HOURS = 12;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Crea un nuevo Refresh Token para el usuario.
     * Si el usuario ya tenía uno activo, lo sobreescribe/elimina para mantener una sola sesión.
     */
    @Transactional
    public RefreshToken createRefreshToken(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Por seguridad, borramos cualquier token viejo que pudiera tener este usuario
        // refreshTokenRepository.deleteByUsuario(usuario);

        // Generamos un UUID aleatorio y seguro
        RefreshToken refreshToken = RefreshToken.builder()
                .usuario(usuario)
                .token(UUID.randomUUID().toString())
                .fechaExpiracion(LocalDateTime.now().plus(REFRESH_TOKEN_EXPIRATION_HOURS, ChronoUnit.HOURS))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Busca el token en la base de datos.
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Verifica si el token ya pasó su fecha de vencimiento.
     * Si expiró, lo elimina de la base de datos por seguridad.
     */
    @Transactional
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("El Refresh Token ha expirado. Por favor, vuelva a iniciar sesión.");
        }
        return token;
    }

    /**
     * Elimina el token de la base de datos (Útil para un endpoint de "Cerrar Sesión").
     */
    @Transactional
    public void eliminarPorUsuario(Usuario usuario) {
        refreshTokenRepository.deleteByUsuario(usuario);
    }
}