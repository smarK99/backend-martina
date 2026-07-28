package com.example.inicial1.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ItemProductoDTO {

    private Long idProducto;
    private Integer cantidadStockProducto;

}
