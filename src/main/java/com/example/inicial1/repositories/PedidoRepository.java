package com.example.inicial1.repositories;

import com.example.inicial1.entities.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends BaseRepository<Pedido, Long>{

    //Buscar historial de pedidos que pertenezcan a una sucursal
    @Query(
            value = "SELECT * FROM pedido WHERE fk_sucursal_id = %:idSucursal%",
            nativeQuery = true
    )
    public List<Pedido> buscarPedidosPorSucursal( @Param("idSucursal") Long idSucursal );

}
