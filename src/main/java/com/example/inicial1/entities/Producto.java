package com.example.inicial1.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
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

    @Column(name = "receta_preparacion", columnDefinition = "TEXT")
    private String recetaPreparacion;

    // NUEVO: Columna para guardar la imagen en formato Base64
    @Column(name = "imagen_producto", columnDefinition = "LONGTEXT")
    private String imagenProducto;

    @Column(name = "fecha_hora_alta_producto", nullable = false)
    private LocalDateTime fechaHoraAltaProducto;

    @Column(name = "fecha_hora_baja_producto")
    private LocalDateTime fechaHoraBajaProducto;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "fk_categoria_id")
    private Categoria categoria;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_producto_id")
    private List<ProductoInsumo> productoInsumoList;

}