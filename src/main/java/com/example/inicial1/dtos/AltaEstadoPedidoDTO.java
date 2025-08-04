package com.example.inicial1.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AltaEstadoPedidoDTO {

    private String nombreEstadoPedido;

    private String descripcionEstadoPedido;
}
