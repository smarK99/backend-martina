package com.example.inicial1.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AltaTipoUsuarioDTO {

    private String nombreTipoUsuario;

    private String descripcionTipoUsuario;

}
