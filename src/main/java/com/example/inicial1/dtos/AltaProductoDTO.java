package com.example.inicial1.dtos;

import lombok.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AltaProductoDTO {

    private String nombreProducto;

    private String descripcionProducto;

    private String recetaPreparacion;

    // NUEVO: Atributo para recibir la imagen en Base64 desde Angular
    private String imagenProducto;

    private Long idCategoria;

    private List<AltaProductoInsumoDTO> apiList;
}