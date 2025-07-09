package com.example.inicial1.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AltaUsuarioDTO {
    private String username;
    private String password;
    private String nombreCompletoUsuario;
    private String dni;
    private String email;
    private String telefono;
    private String direccion;
    private Long idTipoUsuario;
}
