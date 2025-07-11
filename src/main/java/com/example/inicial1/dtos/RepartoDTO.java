package com.example.inicial1.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RepartoDTO {

    private String nombreReparto;
    private String descripcionReparto;
    private Long idUsuario;

}
