package com.example.inicial1.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AltaCategoriaDTO {
    private String nombreCategoria;
    private String descripcionCategoria;
}
