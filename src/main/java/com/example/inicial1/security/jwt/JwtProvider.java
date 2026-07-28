package com.example.inicial1.security.jwt;

import com.example.inicial1.entities.Usuario;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    // Estas propiedades las definiremos en el application.properties
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication) {
        Usuario usuarioPrincipal = (Usuario) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(usuarioPrincipal.getUsername())
                .claim("authorities", authentication.getAuthorities()) // <--- AGREGAR ESTA LÍNEA
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000L))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getNombreUsuarioFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            // Log: Token mal formado
        } catch (UnsupportedJwtException e) {
            // Log: Token no soportado
        } catch (ExpiredJwtException e) {
            // Log: Token expirado
        } catch (IllegalArgumentException e) {
            // Log: Token vacío
        } catch (SignatureException e) {
            // Log: Fallo en la firma
        }
        return false;
    }


}