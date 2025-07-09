package com.example.inicial1.entities;

import com.example.inicial1.enums.Rol;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
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
    private LocalDateTime fechaHoraAltaUsuario;

    @Column(name = "fecha_hora_baja_usuario", nullable = true)
    private LocalDateTime fechaHoraBajaUsuario;

    //Relaciones
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_tipo_usuario",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_usuario_id")
    )
    private List<TipoUsuario> tipoUsuarioList;


//    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    @JoinColumn(name = "fk_usuario_id")
//    private List<TipoUsuario> tipoUsuarioList;
}
