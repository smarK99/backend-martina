package com.example.inicial1.dtos;

import com.example.inicial1.entities.DetallePedido;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PedidosPorSucursalDTO {

    private Long idPedido;
    private String descripcionPedido;
    private LocalDateTime fechaHoraAltaPedido;
    private LocalDateTime fechaHoraBajaPedido;
    private Double totalImportePedido;
    private String nombreEstadoPedido;
    private List<DetallePedidoDTO2> detallePedidoDTO2List;
}
