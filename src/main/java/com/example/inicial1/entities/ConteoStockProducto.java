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
@Table(name = "conteo_stock_producto")
public class ConteoStockProducto extends Base{

    @Column(name = "cantidad_stock_producto")
    private Integer cantidadStockProducto;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "fk_producto_id")
    private Producto producto;

}
