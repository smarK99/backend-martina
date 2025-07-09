package com.example.inicial1.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DetallePedidoDTO2 {//Usado para mostrar pedidos por sucursal

    private Long idProducto;
    private String nombreProducto;
    private Integer cantidadDP;
    private Double subtotalDP;
}
