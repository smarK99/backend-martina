package com.example.inicial1.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AltaProductoInsumoDTO {

    private Long idInsumo;

    private Integer cantidadI;
}
