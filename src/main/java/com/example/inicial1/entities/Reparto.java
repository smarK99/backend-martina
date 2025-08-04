package com.example.inicial1.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
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

    @Column(name = "fecha_hora_inicio_reparto", nullable = false)
    private LocalDateTime fechaHoraInicioReparto;

    @Column(name = "fecha_hora_fin_reparto", nullable = true)
    private LocalDateTime fechaHoraFinReparto;

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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_rendicion_id")
    private Rendicion rendicion;
}

