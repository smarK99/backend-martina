package com.example.inicial1.repositories;

import com.example.inicial1.entities.SucursalProducto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SucursalProductoRepository extends BaseRepository<SucursalProducto, Long>{

//    @Query("SELECT sp FROM SucursalProducto sp WHERE sp.sucursal.id = :idSucursal")
    @Query(value = "SELECT * FROM sucursal_producto WHERE fk_sucursal_id = :idSucursal", nativeQuery = true)
    public List<SucursalProducto> obtenerPreciosPorSucursal(@Param("idSucursal") Long idSucursal );

}
