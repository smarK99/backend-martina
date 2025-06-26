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
@Table(name = "sucursal")
public class Sucursal extends Base{

    @Column(name = "nombre_sucursal", nullable = false)
    private String nombreSucursal;

    @Column(name = "descripcion_sucursal", nullable = false)
    private String descripcionSucursal;

    @Column(name = "direccion_sucursal", nullable = false)
    private String direccionSucursal;

    @Column(name = "fecha_hora_alta_sucursal", nullable = false)
    private LocalDate fechaHoraAltaSucursal;

    @Column(name = "fecha_hora_baja_sucursal", nullable = true)
    private LocalDate fechaHoraBajaSucursal;

    //Relaciones

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_usuario_id")
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_sucursal_id")
    //@Builder.Default
    private List<SucursalProducto> sucursalProductoList;


}
