package com.example.inicial1.dtos;

import com.example.inicial1.enums.MetodoPago;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EntregarPedidoDTO {

    private Long idReparto;

    private Long idPedido;

    private MetodoPago metodoPago;

}
