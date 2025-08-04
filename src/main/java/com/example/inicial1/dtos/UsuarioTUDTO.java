package com.example.inicial1.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UsuarioTUDTO {//Agregar Rol Usuario

    private Long idUsuario;
    private Long idTipoUsuario;
}
