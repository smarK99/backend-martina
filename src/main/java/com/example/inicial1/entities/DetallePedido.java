package com.example.inicial1.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
@Table(name = "detalle_pedido")
public class DetallePedido extends Base{

    @Column(name = "cantidad_detalle_pedido", nullable = false)
    private Integer cantidadDetallePedido;

    @Column(name = "subtotal_detalle_pedido", nullable = false)
    private Double subtotalDetallePedido;

    //Relaciones
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_producto_id")
    private Producto producto;


}
