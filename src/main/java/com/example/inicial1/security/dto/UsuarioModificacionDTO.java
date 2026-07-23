package com.example.inicial1.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioModificacionDTO {

    @NotNull(message = "El código de usuario es obligatorio")
    private Long codUsuario;

    @NotBlank(message = "El nombre y apellido es obligatorio")
    private String nombreApellido;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo es inválido")
    private String mailUsuario;

    private String telefonoUsuario;
    private String domicilioUsuario;

    // --- CONSTRUCTORES ---
    public UsuarioModificacionDTO() {}

    public UsuarioModificacionDTO(Long codUsuario, String nombreApellido, String mailUsuario,
                                  String telefonoUsuario, String domicilioUsuario) {
        this.codUsuario = codUsuario;
        this.nombreApellido = nombreApellido;
        this.mailUsuario = mailUsuario;
        this.telefonoUsuario = telefonoUsuario;
        this.domicilioUsuario = domicilioUsuario;
    }

    // --- GETTERS Y SETTERS ---
    public Long getCodUsuario() { return codUsuario; }
    public void setCodUsuario(Long codUsuario) { this.codUsuario = codUsuario; }

    public String getNombreApellido() { return nombreApellido; }
    public void setNombreApellido(String nombreApellido) { this.nombreApellido = nombreApellido; }

    public String getMailUsuario() { return mailUsuario; }
    public void setMailUsuario(String mailUsuario) { this.mailUsuario = mailUsuario; }

    public String getTelefonoUsuario() { return telefonoUsuario; }
    public void setTelefonoUsuario(String telefonoUsuario) { this.telefonoUsuario = telefonoUsuario; }

    public String getDomicilioUsuario() { return domicilioUsuario; }
    public void setDomicilioUsuario(String domicilioUsuario) { this.domicilioUsuario = domicilioUsuario; }
}