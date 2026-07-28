package com.example.inicial1.security.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

import lombok.Data;

@Data // Esto de Lombok crea los Getters y Setters automáticamente
public class UsuarioRolDTO {

    @NotNull(message = "El código de usuario es obligatorio")
    private Long codUsuario;

    @NotEmpty(message = "La lista de roles no puede estar vacía")
    private List<String> roles;

    // --- CONSTRUCTORES ---
    public UsuarioRolDTO() {}

    public UsuarioRolDTO(Long codUsuario, List<String> roles) {
        this.codUsuario = codUsuario;
        this.roles = roles;
    }

    // --- GETTERS Y SETTERS ---
    public Long getCodUsuario() { return codUsuario; }
    public void setCodUsuario(Long codUsuario) { this.codUsuario = codUsuario; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}