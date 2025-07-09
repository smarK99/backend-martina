package com.example.inicial1.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PreciosPorSucursalDTO {

    private String nombreSucursal;
    private String nombreProducto;
    private Double precioSP;

}
