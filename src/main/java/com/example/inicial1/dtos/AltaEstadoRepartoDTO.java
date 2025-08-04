package com.example.inicial1.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AltaEstadoRepartoDTO {

    private String nombreEstadoReparto;

    private String descripcionEstadoReparto;
}
