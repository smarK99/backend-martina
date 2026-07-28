package com.example.inicial1.dtos;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UpdateControlStockDTO {

    private Long idUsuario;

    private List<ItemProductoDTO> productoDTOList;

    private List<ItemInsumoDTO> insumoDTOList;

}
