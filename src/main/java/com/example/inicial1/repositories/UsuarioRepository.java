package com.example.inicial1.repositories;

import com.example.inicial1.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {

    // Método fundamental para la autenticación
    Optional<Usuario> findByUsername(String username);

    // Búsqueda por email para la recuperación de contraseña
    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByFechaHoraBajaUsuarioIsNull();

    @Query(
            value = "SELECT * FROM usuario WHERE fecha_hora_baja_usuario IS NULL",
            nativeQuery = true
    )
    List<Usuario> obtenerTodos();

    // ==========================================
    // NUEVO: Búsqueda paginada y filtrada por texto (Nombre, Username o Email)
    // ==========================================
    @Query("SELECT u FROM Usuario u WHERE " +
            "u.fechaHoraBajaUsuario IS NULL AND " +
            "(:termino IS NULL OR :termino = '' OR " +
            " LOWER(u.nombreCompletoUsuario) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
            " LOWER(u.username) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
            " LOWER(u.email) LIKE LOWER(CONCAT('%', :termino, '%'))) " +
            "ORDER BY u.id DESC")
    Page<Usuario> buscarPaginadoYFiltrado(@Param("termino") String termino, Pageable pageable);
}