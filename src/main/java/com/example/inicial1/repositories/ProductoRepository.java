package com.example.inicial1.repositories;

import com.example.inicial1.entities.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends BaseRepository<Producto, Long>{

    @Query(
            value = "SELECT * FROM producto WHERE fecha_hora_baja_producto IS NULL",
            nativeQuery = true
    )
    public List<Producto> obtenerTodos();
}
