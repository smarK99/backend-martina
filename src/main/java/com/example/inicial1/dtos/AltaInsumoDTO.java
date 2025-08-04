package com.example.inicial1.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AltaInsumoDTO {

    private String nombreInsumo;

    private String descripcionInsumo;

    private Double precioCompraInsumo;

}
