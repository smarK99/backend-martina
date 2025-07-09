package com.example.inicial1.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductoInsumoDTO {

    private Long idProducto;
    private Long idInsumo;
    private Integer cantidadPI;
}
