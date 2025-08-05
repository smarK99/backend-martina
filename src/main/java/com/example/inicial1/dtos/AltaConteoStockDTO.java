package com.example.inicial1.dtos;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AltaConteoStockDTO {

    private Long idUsuario;

    private List<AltaConteoStockProductoDTO> productosDTOList;

    private List<AltaConteoStockInsumoDTO> insumosDTOList;
}
