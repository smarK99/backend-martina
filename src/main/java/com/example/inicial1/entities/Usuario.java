package com.example.inicial1.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
@Table(name = "usuario")
public class Usuario extends Base implements UserDetails{

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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_tipo_usuario",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_usuario_id")
    )
    private List<TipoUsuario> tiposUsuario;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Transformamos tus TipoUsuario en permisos que Spring Security entiende
        return tiposUsuario.stream()
                .map(tipo -> new SimpleGrantedAuthority(tipo.getNombreTipoUsuario()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() {
        // Si tienes fechaHoraBajaUsuario, el usuario está activo si es nula
        return fechaHoraBajaUsuario == null;
    }

    @Override
    public String getPassword() {
        return this.password; // <-- Forzamos explícitamente a Spring a leer tu atributo codificado
    }

    @Override
    public String getUsername() {
        return this.username; // <-- Hacemos lo mismo con el username por seguridad
    }

//    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    @JoinColumn(name = "fk_usuario_id")
//    private List<TipoUsuario> tipoUsuarioList;
}
