package com.example.inicial1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
@Table(name = "gasto")
public class Gasto extends Base{

    @Column(name = "nombre_gasto")
    private String nombreGasto;

    @Column(name = "monto_gasto")
    private Double montoGasto;
}
