package com.example.inicial1.repositories;

import com.example.inicial1.entities.ConteoStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConteoStockRepository extends BaseRepository<ConteoStock, Long>{

    @Query(
            value = "SELECT * FROM conteo_stock WHERE fecha_hora_baja_conteo_stock IS NULL",
            nativeQuery = true
    )
    public List<ConteoStock> obtenerTodos();

    // ==========================================
    // NUEVA CONSULTA: Paginación, Fecha y Búsqueda por Texto (ID, Usuario, Insumo o Producto)
    // ==========================================
    @Query("SELECT DISTINCT c FROM ConteoStock c " +
            "LEFT JOIN c.usuario u " +
            "LEFT JOIN c.csinsumosList csi " +
            "LEFT JOIN csi.insumo i " +
            "LEFT JOIN c.csproductosList csp " +
            "LEFT JOIN csp.producto p " +
            "WHERE c.fechaHoraBajaConteoStock IS NULL " +
            "AND (:fecha IS NULL OR :fecha = '' OR CAST(c.fechaHoraAltaConteoStock AS string) LIKE CONCAT(:fecha, '%')) " +
            "AND (:termino IS NULL OR :termino = '' " +
            "     OR CAST(c.id AS string) LIKE CONCAT('%', :termino, '%') " +
            "     OR LOWER(u.nombreCompletoUsuario) LIKE LOWER(CONCAT('%', :termino, '%')) " +
            "     OR LOWER(i.nombreInsumo) LIKE LOWER(CONCAT('%', :termino, '%')) " +
            "     OR LOWER(p.nombreProducto) LIKE LOWER(CONCAT('%', :termino, '%'))) " +
            "ORDER BY c.fechaHoraAltaConteoStock DESC")
    Page<ConteoStock> buscarPaginadoYFiltrado(
            @Param("termino") String termino,
            @Param("fecha") String fecha,
            Pageable pageable);
}