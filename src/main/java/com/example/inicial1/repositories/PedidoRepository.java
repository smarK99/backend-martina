package com.example.inicial1.repositories;

import com.example.inicial1.dtos.ResumenVentasDTO;
import com.example.inicial1.dtos.TotalVtasPorSucursalDTO;
import com.example.inicial1.entities.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepository extends BaseRepository<Pedido, Long>{

    // NUEVO: Obtener todos los pedidos paginados y ordenados por fecha descendente (Los más nuevos primero)
    @Query("SELECT p FROM Pedido p ORDER BY p.fechaHoraAltaPedido DESC")
    Page<Pedido> obtenerTodosPaginados(Pageable pageable);

    // NUEVO: Buscar historial de pedidos por sucursal con paginación
    @Query("SELECT p FROM Pedido p WHERE p.sucursal.id = :idSucursal ORDER BY p.fechaHoraAltaPedido DESC")
    Page<Pedido> buscarPedidosPorSucursalPaginados(@Param("idSucursal") Long idSucursal, Pageable pageable);

    // Mantenemos el metodo original corregido (sin los % para el ID numérico)
    // para que no falle tu PedidoServiceImpl actual.
    @Query(
            value = "SELECT * FROM pedido WHERE fk_sucursal_id = :idSucursal",
            nativeQuery = true
    )
    List<Pedido> buscarPedidosPorSucursal(@Param("idSucursal") Long idSucursal);

    // Mantenemos tus otras consultas intactas...

    @Query(value = "SELECT s.nombre_sucursal AS nombreSucursal, SUM(p.importe_total_pedido) AS montoTotalVentas " +
            "FROM pedido p " +
            "INNER JOIN sucursal s ON p.fk_sucursal_id = s.id " +
            "INNER JOIN estado_pedido ep ON p.fk_estado_pedido_id = ep.id " +
            "WHERE ep.nombre_estado_pedido != 'CANCELADO' " +
            "GROUP BY s.id, s.nombre_sucursal " +
            "ORDER BY SUM(p.importe_total_pedido) DESC",
            nativeQuery = true)
    List<TotalVtasPorSucursalDTO> obtenerTotalVentasPorSucursal();

    @Query(value = "SELECT COALESCE(SUM(p.importe_total_pedido), 0) AS totalVentas, " +
            "COUNT(p.id) AS totalPedidos " +
            " FROM pedido p " +
            "INNER JOIN estado_pedido ep ON p.fk_estado_pedido_id = ep.id " +
            "WHERE ep.nombre_estado_pedido != 'CANCELADO' " +
            "AND p.fecha_hora_alta_pedido >= :fechaLimite",
            nativeQuery = true)
    ResumenVentasDTO obtenerRecaudacionUltimos30Dias(@Param("fechaLimite") LocalDate fechaLimite);

    @Query(value = "SELECT COALESCE(SUM(p.importe_total_pedido), 0) " +
            "FROM pedido p " +
            "INNER JOIN estado_pedido ep ON p.fk_estado_pedido_id = ep.id " +
            "WHERE ep.nombre_estado_pedido != 'CANCELADO' " +
            "AND p.fecha_hora_alta_pedido >= :fechaLimite",
            nativeQuery = true)
    Double obtenerRecaudacionUltimos7Dias(@Param("fechaLimite") LocalDate fechaLimite);

    @Query(value = "SELECT p.* " +
            "FROM pedido p " +
            "INNER JOIN estado_pedido ep ON p.fk_estado_pedido_id = ep.id " +
            "WHERE ep.nombre_estado_pedido != 'CANCELADO' " +
            "AND p.fk_reparto_id IS NULL",
            nativeQuery = true)
    List<Pedido> obtenerPedidosDisponiblesParaReparto();


    // NUEVO: Búsqueda dinámica por ID o Importe, respetando el filtro de sucursal
    @Query("SELECT p FROM Pedido p WHERE " +
            "(:idSucursal = 0L OR p.sucursal.id = :idSucursal) AND " +
            "(CAST(p.id AS string) LIKE CONCAT('%', :termino, '%') OR CAST(p.importeTotalPedido AS string) LIKE CONCAT('%', :termino, '%')) " +
            "ORDER BY p.fechaHoraAltaPedido DESC")
    Page<Pedido> buscarPaginadoYFiltrado(@Param("termino") String termino, @Param("idSucursal") Long idSucursal, Pageable pageable);
}