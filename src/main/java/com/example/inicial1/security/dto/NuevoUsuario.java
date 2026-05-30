package com.example.inicial1.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter @Setter
public class NuevoUsuario {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String nombreCompletoUsuario;
    @NotBlank
    private String dni;
    @Email
    private String email;
    private String telefono;
    private String direccion;
    private Set<String> roles; // Nombres de los roles como "ROLE_CLIENTE"
}