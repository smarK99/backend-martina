package com.example.inicial1.repositories;

import com.example.inicial1.entities.Categoria;
import com.example.inicial1.entities.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends BaseRepository<Categoria, Long>{

    @Query(
            value = "SELECT * FROM categoria WHERE fecha_hora_baja_categoria IS NULL",
            nativeQuery = true
    )
    public List<Categoria> obtenerTodos();
}
