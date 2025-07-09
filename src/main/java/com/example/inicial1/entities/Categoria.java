package com.example.inicial1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
@Table(name = "categoria")
public class Categoria extends Base{

    @Column(name = "nombre_categoria", nullable = false)
    private String nombreCategoria;

    @Column(name = "descripcion_categoria", nullable = false)
    private String descripcionCategoria;

    @Column(name = "fecha_hora_alta_categoria", nullable = false)
    private LocalDateTime fechaHoraAltaCategoria;

    @Column(name = "fecha_hora_baja_categoria", nullable = true)
    private LocalDateTime fechaHoraBajaCategoria;

}
