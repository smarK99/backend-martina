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
@Table(name = "detalle_producto")
public class DetallePedido extends Base{

    @Column(name = "cantidad_detalle_pedido", nullable = false)
    private Integer cantidadDetallePedido;

    //Relaciones
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_producto_id")
    private Producto producto;


}
