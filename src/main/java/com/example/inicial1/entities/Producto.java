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
@Table(name = "producto")
public class Producto extends Base{

    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;

    @Column(name = "descripcion_producto", nullable = false)
    private String descripcionProducto;

    @Column(name = "fecha_hora_alta_producto", nullable = false)
    private LocalDate fechaHoraAltaProducto;

    @Column(name = "fecha_hora_baja_producto", nullable = true)
    private LocalDate fechaHoraBajaProducto;

    //Relaciones

    @ManyToOne
    @JoinColumn(name = "fk_categoria_id")
    private Categoria categoria;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_producto_id")
    //@Builder.Default
    private List<ProductoInsumo> productoInsumoList;

}
