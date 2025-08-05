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
@Table(name = "conteo_stock_insumo")
public class ConteoStockInsumo extends Base{

    @Column(name = "cantidad_stock_insumo")
    private Integer cantidadStockInsumo;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "fk_insumo_id")
    private Insumo insumo;

}
