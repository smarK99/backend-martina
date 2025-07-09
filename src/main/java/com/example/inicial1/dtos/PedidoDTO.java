package com.example.inicial1.dtos;

import com.example.inicial1.entities.DetallePedido;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PedidoDTO {

    private Long idSucursal;
    private String descripcionPedido;
    private List<DetallePedidoDTO> dpdtoList;
}
