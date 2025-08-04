package com.example.inicial1.dtos;

import com.example.inicial1.entities.Usuario;
import jakarta.persistence.Column;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AltaSucursalDTO {

    private String nombreSucursal;

    private String descripcionSucursal;

    private String direccionSucursal;

    private Long idUsuario;
}
