package com.example.inicial1.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
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
    private LocalDate fechaHoraAltaPedido;

    @Column(name = "fecha_hora_baja_pedido", nullable = true)
    private LocalDate fechaHoraBajaPedido;

    //Relaciones

    @ManyToOne
    @JoinColumn(name = "fk_estado_pedido_id")
    private EstadoPedido estadoPedido;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_sucursal_id")
    private Sucursal sucursal;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_pedido_id")
    //@Builder.Default
    private List<DetallePedido> detallePedidoList;


}
