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
@Table(name = "producto_insumo")
public class ProductoInsumo extends Base{

    @Column(name = "cantidad_insumo", nullable = false)
    private Integer cantidadInsumo;

    //Relaciones

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_insumo_id")
    private Insumo insumo;
}
