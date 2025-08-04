package com.example.inicial1.dtos;

import jakarta.persistence.Column;
import lombok.*;

import java.util.List;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AltaProductoDTO {

    private String nombreProducto;

    private String descripcionProducto;

    private Long idCategoria;

    private List<AltaProductoInsumoDTO> apiList;
}
