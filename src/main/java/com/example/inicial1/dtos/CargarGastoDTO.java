package com.example.inicial1.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CargarGastoDTO {

    private Long idReparto;

    private String nombreGasto;

    private Double montoGasto;
}
