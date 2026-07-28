package com.example.inicial1.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginUsuario {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}