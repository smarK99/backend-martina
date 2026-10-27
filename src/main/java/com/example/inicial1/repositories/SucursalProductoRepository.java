package com.example.inicial1.repositories;

import com.example.inicial1.entities.SucursalProducto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SucursalProductoRepository extends BaseRepository<SucursalProducto, Long>{

//    @Query("SELECT sp FROM SucursalProducto sp WHERE sp.sucursal.id = :idSucursal")
    @Query(value = "SELECT sp FROM SucursalProducto sp \n" +
            "WHERE sp.sucursal.id = :idSucursal \n" +
            "AND sp.fechaHoraUltimaModificacion = (\n" +
            "    SELECT MAX(sp2.fechaHoraUltimaModificacion) \n" +
            "    FROM SucursalProducto sp2 \n" +
            "    WHERE sp2.producto.id = sp.producto.id AND sp2.sucursal.id = :idSucursal\n" +
            ")", nativeQuery = true)
    public List<SucursalProducto> obtenerPreciosPorSucursal(@Param("idSucursal") Long idSucursal );

}
