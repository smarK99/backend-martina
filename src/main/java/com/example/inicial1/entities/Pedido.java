package com.example.inicial1.entities;

import com.example.inicial1.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
@Table(name = "pedido")
public class Pedido extends Base{

    @Column(name = "descripcion_pedido", nullable = false)
    private String descripcionPedido;

    @Column(name = "fecha_hora_alta_pedido", nullable = false)
    private LocalDateTime fechaHoraAltaPedido;

    @Column(name = "fecha_hora_baja_pedido", nullable = true)
    private LocalDateTime fechaHoraBajaPedido;

    @Column(name = "importe_total_pedido")
    private Double importeTotalPedido;

    //Enum
    @Column(name = "metodo_pago", nullable = true)
    private MetodoPago metodoPago;

    //Relaciones

    @ManyToOne
    @JoinColumn(name = "fk_estado_pedido_id")
    private EstadoPedido estadoPedido;

    //cascade type persist hace que las sucursales no se guarden en la bdd hasta que se las vincule a un pedido
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_sucursal_id")
    private Sucursal sucursal;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_pedido_id")
    //@Builder.Default
    private List<DetallePedido> detallePedidoList;


}
