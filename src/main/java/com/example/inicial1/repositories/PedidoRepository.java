package com.example.inicial1.repositories;

import com.example.inicial1.dtos.ResumenVentasDTO;
import com.example.inicial1.dtos.TotalVtasPorSucursalDTO;
import com.example.inicial1.entities.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepository extends BaseRepository<Pedido, Long>{

    //Buscar historial de pedidos que pertenezcan a una sucursal
    @Query(
            value = "SELECT * FROM pedido WHERE fk_sucursal_id = %:idSucursal%",
            nativeQuery = true
    )
    public List<Pedido> buscarPedidosPorSucursal( @Param("idSucursal") Long idSucursal );

    //Buscar todos los pedidos y calcular el monto total de ventas por sucursal
    @Query(value = "SELECT s.nombre_sucursal AS nombreSucursal, SUM(p.importe_total_pedido) AS montoTotalVentas " +
            "FROM pedido p " +
            "INNER JOIN sucursal s ON p.fk_sucursal_id = s.id " +
            "INNER JOIN estado_pedido ep ON p.fk_estado_pedido_id = ep.id " +
            "WHERE ep.nombre_estado_pedido != 'CANCELADO' " +
            "GROUP BY s.id, s.nombre_sucursal " +
            "ORDER BY SUM(p.importe_total_pedido) DESC",
            nativeQuery = true)
    public List<TotalVtasPorSucursalDTO> obtenerTotalVentasPorSucursal();


    //Obtener el total del dinero recaudado en los ultimos 30 dias y la cantidad de pedidos realizados
    //Calculamos la fecha antes para no tener problemas con el cambio de bdd
    @Query(value = "SELECT COALESCE(SUM(p.importe_total_pedido), 0) AS totalVentas, " +
            "COUNT(p.id) AS totalPedidos " +
            " FROM pedido p " +
            "INNER JOIN estado_pedido ep ON p.fk_estado_pedido_id = ep.id " +
            "WHERE ep.nombre_estado_pedido != 'CANCELADO' " +
            "AND p.fecha_hora_alta_pedido >= :fechaLimite",
            nativeQuery = true)
    public ResumenVentasDTO obtenerRecaudacionUltimos30Dias(@Param("fechaLimite") LocalDate fechaLimite);

    //Obtener el total del dinero recaudado en los ultimos 7 dias
    @Query(value = "SELECT COALESCE(SUM(p.importe_total_pedido), 0) " +
            "FROM pedido p " +
            "INNER JOIN estado_pedido ep ON p.fk_estado_pedido_id = ep.id " +
            "WHERE ep.nombre_estado_pedido != 'CANCELADO' " +
            "AND p.fecha_hora_alta_pedido >= :fechaLimite",
            nativeQuery = true)
    public Double obtenerRecaudacionUltimos7Dias(@Param("fechaLimite") LocalDate fechaLimite);

    //Obtener pedidos no asignados a un reparto y que no estan en estado cancelado
    @Query(value = "SELECT p.* " +
            "FROM pedido p " +
            "INNER JOIN estado_pedido ep ON p.fk_estado_pedido_id = ep.id " +
            "WHERE ep.nombre_estado_pedido != 'CANCELADO' " +
            "AND p.fk_reparto_id IS NULL",
            nativeQuery = true)
    public List<Pedido> obtenerPedidosDisponiblesParaReparto();


}