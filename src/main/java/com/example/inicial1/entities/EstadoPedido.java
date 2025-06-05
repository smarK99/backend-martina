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
@Table(name = "estado_pedido")
public class EstadoPedido extends Base {

    @Column(name = "nombre_estado_pedido", nullable = false)
    private String nombreEstadoPedido;

    @Column(name = "descripcion_estado_pedido", nullable = false)
    private String descripcionEstadoPedido;

    @Column(name = "fecha_hora_alta_estado_pedido", nullable = false)
    private LocalDate fechaHoraAltaEstadoPedido;

    @Column(name = "fecha_hora_baja_estado_pedido", nullable = true)
    private LocalDate fechaHoraBajaEstadoPedido;


}
