package com.example.inicial1.entities;

import com.example.inicial1.enums.Rol;
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
@Table(name = "usuario")
public class Usuario extends Base{

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nombre_completo_usuario", nullable = false)
    private String nombreCompletoUsuario;

    @Column(name = "dni", nullable = false)
    private String dni;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "fecha_hora_alta_usuario", nullable = false)
    private LocalDate fechaHoraAltaUsuario;

    @Column(name = "fecha_hora_baja_usuario", nullable = true)
    private LocalDate fechaHoraBajaUsuario;

    //Relaciones

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_usuario_id")
    private List<TipoUsuario> tipoUsuarioList;
}
