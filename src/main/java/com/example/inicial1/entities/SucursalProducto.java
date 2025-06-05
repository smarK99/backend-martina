package com.example.inicial1.entities;

import jakarta.persistence.*;
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
@Table(name = "sucursal_producto")
public class SucursalProducto extends Base{

    @Column(name = "precio_sucursal_producto", nullable = false)
    private Double precioSucursalProducto;

    @Column(name = "fecha_hora_ultima_modif", nullable = false)
    private LocalDate fechaHoraUltModif;

    //Relaciones

    @ManyToOne
    @JoinColumn(name = "fk_producto_id")
    private Producto producto;
}
