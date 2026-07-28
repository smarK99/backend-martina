package com.example.inicial1.repositories;

import com.example.inicial1.entities.Categoria;
import com.example.inicial1.entities.ConteoStock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConteoStockRepository extends BaseRepository<ConteoStock, Long>{

    @Query(
            value = "SELECT * FROM conteo_stock WHERE fecha_hora_baja_conteo_stock IS NULL",
            nativeQuery = true
    )
    public List<ConteoStock> obtenerTodos();
}
