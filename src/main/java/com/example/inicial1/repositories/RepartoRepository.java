package com.example.inicial1.repositories;

import com.example.inicial1.entities.Reparto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepartoRepository extends BaseRepository<Reparto, Long>{

    @Query("SELECT DISTINCT r FROM Reparto r " +
            "LEFT JOIN r.usuario u " +
            "WHERE (:idRepartidor = 0 OR u.id = :idRepartidor) " +
            "AND (:idEstado = 0 OR r.estadoReparto.id = :idEstado) " + // <-- NUEVO FILTRO POR ESTADO
            "AND (:fecha IS NULL OR :fecha = '' OR CAST(r.fechaHoraInicioReparto AS string) LIKE CONCAT(:fecha, '%')) " +
            "AND (:termino IS NULL OR :termino = '' " +
            "     OR CAST(r.id AS string) LIKE CONCAT('%', :termino, '%') " +
            "     OR LOWER(u.nombreCompletoUsuario) LIKE LOWER(CONCAT('%', :termino, '%'))) " +
            "ORDER BY r.fechaHoraInicioReparto DESC")
    Page<Reparto> buscarPaginadoYFiltrado(
            @Param("termino") String termino,
            @Param("idRepartidor") Integer idRepartidor,
            @Param("idEstado") Long idEstado, // <-- AGREGADO
            @Param("fecha") String fecha,
            Pageable pageable);
}