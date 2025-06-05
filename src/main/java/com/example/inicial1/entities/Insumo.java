package com.example.inicial1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
@Table(name = "insumo")
public class Insumo extends Base{

    @Column(name = "nombre_insumo", nullable = false)
    private String nombreInsumo;

    @Column(name = "descripcion_insumo", nullable = false)
    private String descripcionInsumo;

    @Column(name = "precio_compra_insumo", nullable = false)
    private Double precioCompraInsumo;

    @Column(name = "fecha_hora_alta_insumo", nullable = false)
    private LocalDate fechaHoraAltaInsumo;

    @Column(name = "fecha_hora_baja_insumo", nullable = true)
    private LocalDate fechaHoraBajaInsumo;

}
