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
@Table(name = "estado_reparto")
public class EstadoReparto extends Base{

    @Column(name = "nombre_estado_reparto", nullable = false)
    private String nombreEstadoReparto;

    @Column(name = "descripcion_estado_reparto", nullable = false)
    private String descripcionEstadoReparto;

    @Column(name = "fecha_hora_alta_estado_reparto", nullable = false)
    private LocalDate fechaHoraAltaEstadoReparto;

    @Column(name = "fecha_hora_baja_estado_reparto", nullable = true)
    private LocalDate fechaHoraBajaEstadoReparto;

}
