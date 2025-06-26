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
@Table(name = "conteo_stock")
public class ConteoStock extends Base{

    @Column(name = "fecha_hora_alta_conteo_stock", nullable = false)
    private LocalDate fechaHoraAltaConteoStock;

    //Relaciones
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_conteo_stock_id")
    //@Builder.Default
    private List<Insumo> insumosList;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_conteo_stock_id")
    //@Builder.Default
    private List<Producto> productosList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario_id")
    private Usuario usuario;

}
