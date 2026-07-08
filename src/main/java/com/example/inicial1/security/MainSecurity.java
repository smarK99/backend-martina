package com.example.inicial1.security;

import com.example.inicial1.security.jwt.JwtEntryPoint;
import com.example.inicial1.security.jwt.JwtTokenFilter;
import com.example.inicial1.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class MainSecurity {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Le decimos dónde buscar al usuario
        authProvider.setPasswordEncoder(passwordEncoder()); // Le decimos que use BCrypt
        return authProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Sintaxis actualizada para Spring Security 6
                .cors(org.springframework.security.config.Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/producto/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll() // Login y Registro público
                        .requestMatchers("/v3/api-docs/**",
                                                    "/swagger-ui/**",
                                                    "/swagger-ui.html",
                                                    "/v3/api-docs",
                                                    "/api-docs/**",
                                                    "/swagger-ui.html",
                                                    "/swagger-ui/**",
                                                    "/swagger-resources",
                                                    "/swagger-resources/**",
                                                    "/webjars/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll() // <-- PERMISO PARA LA BASE DE DATOS H2
                        .anyRequest().authenticated() // Todo lo demás requiere login
                )
                .exceptionHandling(exc -> exc.authenticationEntryPoint(jwtEntryPoint))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 2. Permiso para que H2 pueda renderizar sus paneles (Frames)
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
        // Permitimos específicamente el puerto de Angular
        configuration.setAllowedOrigins(java.util.List.of("http://localhost:4200"));
        // Permitimos todos los métodos, incluyendo el famoso OPTIONS que nos está fallando
        configuration.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Permitimos que viajen los tokens en las cabeceras
        configuration.setAllowedHeaders(java.util.List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}