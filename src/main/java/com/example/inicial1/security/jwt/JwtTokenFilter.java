package com.example.inicial1.security.jwt;

import com.example.inicial1.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getToken(req);
            if (token != null) {
                System.out.println(">>> DEBUG [Filtro]: Token recibido desde Angular: " + token.substring(0, Math.min(token.length(), 20)) + "...");
                if (jwtProvider.validateToken(token)) {
                    String nombreUsuario = jwtProvider.getNombreUsuarioFromToken(token);
                    System.out.println(">>> DEBUG [Filtro]: Token validado OK para el usuario: " + nombreUsuario);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    System.out.println(">>> DEBUG [Filtro]: El token llegó pero el Provider dijo que es INVÁLIDO.");
                }
            } else {
                System.out.println(">>> DEBUG [Filtro]: Angular no mandó token en la cabecera para la ruta: " + req.getRequestURI());
            }
        } catch (Exception e) {
            System.out.println(">>> DEBUG [Filtro - ERROR FATAL]: " + e.getMessage());
            e.printStackTrace();
        }
        filterChain.doFilter(req, res);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Le decimos al filtro que ni se moleste en revisar el Token en estas rutas
        return path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/h2-console") ||
                path.startsWith("/api/auth");
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer "))
            return header.replace("Bearer ", "");
        return null;
    }
}
