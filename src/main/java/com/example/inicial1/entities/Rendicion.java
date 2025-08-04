package com.example.inicial1.entities;

import com.fasterxml.jackson.core.io.schubfach.FloatToDecimal;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
@Table(name = "rendicion")
public class Rendicion extends Base{

    @Column(name = "monto_rendido") //Suma del efectivo rendido ante el encargado
    private Double montoRendido;

    @Column(name = "monto_recaudado") //Suma de los pedidos cobrados en efectivo
    private Double montoRecaudado;

    @Column(name = "diferencia")
    private Double diferencia;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_rendicion_id")
    private List<Gasto> gastos;
}
