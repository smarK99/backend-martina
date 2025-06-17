package com.example.inicial1.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
@Table(name = "reparto")
public class Reparto extends Base {

    @Column(name = "nombre_reparto", nullable = false)
    private String nombreReparto;

    @Column(name = "descripcion_reparto", nullable = false)
    private String descripcionReparto;

    @Column(name = "fecha_hora_alta_reparto", nullable = false)
    private LocalDate fechaHoraAltaReparto;

    @Column(name = "fecha_hora_baja_reparto", nullable = true)
    private LocalDate fechaHoraBajaReparto;

    //Relaciones

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_reparto_id")
    //@Builder.Default
    private List<Pedido> pedidosList;

    @ManyToOne
    @JoinColumn(name = "fk_estado_reparto_id")
    private EstadoReparto estadoReparto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_usuario_id")
    private Usuario usuario;


}

