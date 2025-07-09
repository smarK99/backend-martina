package com.example.inicial1.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SucursalProductoDTO{

    private Long idProducto;
    private Long idSucursal;
    private Double precioSP;

}
