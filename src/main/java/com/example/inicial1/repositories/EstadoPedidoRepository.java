package com.example.inicial1.repositories;

import com.example.inicial1.entities.Categoria;
import com.example.inicial1.entities.EstadoPedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoPedidoRepository extends BaseRepository<EstadoPedido, Long>{

    @Query(
            value = "SELECT * FROM estado_pedido WHERE fecha_hora_baja_estado_pedido IS NULL",
            nativeQuery = true
    )
    public List<EstadoPedido> obtenerTodos();

}
