package com.example.inicial1.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
@Table(name = "movimiento_cta_cte")
public class MovimientoCtaCte extends Base{

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "concepto")
    private String concepto; // Ej: "Pedido #45", "Abono Efectivo", "Abono Transferencia"

    @Column(name = "importe_pedido", nullable = false)
    private Double importePedido; // El valor de lo que compra

    @Column(name = "monto_pagado", nullable = false)
    private Double montoPagado; // La plata física que entrega

    @Column(name = "saldo_restante", nullable = false)
    private Double saldoRestante; // El cálculo final (El "Debe")

    // --- Relaciones ---

    // Un movimiento pertenece obligatoriamente a una sucursal (Cliente)
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_sucursal")
    private Sucursal sucursal;
}
