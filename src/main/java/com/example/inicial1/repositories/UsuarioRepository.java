package com.example.inicial1.repositories;

import com.example.inicial1.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {

    // Método fundamental para la autenticación
    Optional<Usuario> findByUsername(String username);

    // También es útil tener la búsqueda por email para la recuperación de contraseña (CU N°7)
    Optional<Usuario> findByEmail(String email);

    // Tu consulta actual está bien, pero puedes usar Query Methods de Spring Data
    // para que sea más limpio y no dependa de SQL nativo
    List<Usuario> findByFechaHoraBajaUsuarioIsNull();

    @Query(
            value = "SELECT * FROM usuario WHERE fecha_hora_baja_usuario IS NULL",
            nativeQuery = true
    )
    List<Usuario> obtenerTodos();
}