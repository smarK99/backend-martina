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
@Table(name = "tipo_usuario")
public class TipoUsuario extends Base{

    //Cliente, Repartidor, Dueño, Admin

    @Column(name = "nombre_tipo_usuario", nullable = false)
    private String nombreTipoUsuario;

    @Column(name = "descripcion_tipo_usuario", nullable = false)
    private String descripcionTipoUsuario;

    @Column(name = "fecha_hora_inicio_vigencia_tu", nullable = false)
    private LocalDate fechaHoraInicioVigenciaTipoUsuario;

    @Column(name = "fecha_hora_fin_vigencia_tu", nullable = true)
    private LocalDate fechaHoraFinVigenciaTipoUsuario;

}
