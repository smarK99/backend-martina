package com.example.inicial1.repositories;

import com.example.inicial1.dtos.ProductosMasVendidos;
import com.example.inicial1.entities.DetallePedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DetallePedidoRepository extends BaseRepository<DetallePedido, Long>{

    //Obtener Producto Mas vendido
    /*
    * Ir a la tabla de DETALLE_PEDIDO
    *  en donde tenemos todas las cantidad_detalle_pedido
    *  de lo que se pidio en ese pedido, la fk_producto_id (corresponde al producto)
    *  y la fk_pedido_id que es el link del pedido al que pertenece
    * Entonces se traeria la tabla entera de DETALLE_PEDIDO y
    * se iria sumando para cada fk_producto_id las cantidad_detalle_pedido.
    * Tambien se suma cada subtotal de cada item para obtener un monto total de venta por item
    * Se traen los pedidos de los ultimos treinta dias
    * Luego ordenariamos de mayor a menor.
    * */
    @Query(value = "SELECT p.nombre_producto AS nombreProducto, " +
            "SUM(dp.cantidad_detalle_pedido) AS cantidadTotal, " +
            "SUM(dp.subtotal_detalle_pedido) AS montoTotal " +
            "FROM detalle_pedido dp " +
            "INNER JOIN producto p ON dp.fk_producto_id = p.id " +
            "INNER JOIN pedido ped ON dp.fk_pedido_id = ped.id " +
            "INNER JOIN estado_pedido ep ON ped.fk_estado_pedido_id = ep.id " +
            "WHERE ep.nombre_estado_pedido != 'CANCELADO' " +
            "AND ped.fecha_hora_alta_pedido >= :fechaLimite " +
            "GROUP BY p.id, p.nombre_producto " +
            "ORDER BY cantidadTotal DESC",
            nativeQuery = true)
    List<ProductosMasVendidos> obtenerProdsMasVendidos(@Param("fechaLimite") LocalDate fechaLimite);
}
