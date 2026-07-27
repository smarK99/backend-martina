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
            System.out.println(">>> DEBUG [Provider]: Token mal formado (A veces pasa si Angular manda el token con comillas).");
        } catch (UnsupportedJwtException e) {
            System.out.println(">>> DEBUG [Provider]: Token no soportado.");
        } catch (ExpiredJwtException e) {
            System.out.println(">>> DEBUG [Provider]: El Token ya expiró, hay que iniciar sesión de nuevo.");
        } catch (IllegalArgumentException e) {
            System.out.println(">>> DEBUG [Provider]: Token vacío.");
        } catch (SignatureException e) {
            System.out.println(">>> DEBUG [Provider]: Falló la firma. La secret no coincide.");
        }
        return false;
    }


    // Metodo exclusivo para recuperación de contraseña (dura 15 minutos)
    public String generateResetToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 15 * 60 * 1000L)) // 15 minutos en milisegundos
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                // Usá tu variable de tiempo de expiración normal (ej: 15 minutos)
                .setExpiration(new Date((new Date()).getTime() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

}