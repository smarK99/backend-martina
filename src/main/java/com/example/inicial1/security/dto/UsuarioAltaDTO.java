package com.example.inicial1.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioAltaDTO {

    @NotBlank(message = "El nombre y apellido es obligatorio")
    private String nombreApellido;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, message = "El usuario debe tener al menos 4 caracteres")
    private String usuario;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo es inválido")
    private String mailUsuario;

    private String telefonoUsuario;
    private String domicilioUsuario;

    @NotBlank(message = "La contraseña es obligatoria")
    private String claveUsuario;

    // --- CONSTRUCTORES ---
    public UsuarioAltaDTO() {}

    public UsuarioAltaDTO(String nombreApellido, String usuario, String mailUsuario,
                          String telefonoUsuario, String domicilioUsuario, String claveUsuario) {
        this.nombreApellido = nombreApellido;
        this.usuario = usuario;
        this.mailUsuario = mailUsuario;
        this.telefonoUsuario = telefonoUsuario;
        this.domicilioUsuario = domicilioUsuario;
        this.claveUsuario = claveUsuario;
    }

    // --- GETTERS Y SETTERS ---
    public String getNombreApellido() { return nombreApellido; }
    public void setNombreApellido(String nombreApellido) { this.nombreApellido = nombreApellido; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getMailUsuario() { return mailUsuario; }
    public void setMailUsuario(String mailUsuario) { this.mailUsuario = mailUsuario; }

    public String getTelefonoUsuario() { return telefonoUsuario; }
    public void setTelefonoUsuario(String telefonoUsuario) { this.telefonoUsuario = telefonoUsuario; }

    public String getDomicilioUsuario() { return domicilioUsuario; }
    public void setDomicilioUsuario(String domicilioUsuario) { this.domicilioUsuario = domicilioUsuario; }

    public String getClaveUsuario() { return claveUsuario; }
    public void setClaveUsuario(String claveUsuario) { this.claveUsuario = claveUsuario; }
}