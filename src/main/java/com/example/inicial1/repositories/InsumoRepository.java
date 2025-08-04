package com.example.inicial1.repositories;

import com.example.inicial1.entities.Insumo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsumoRepository extends BaseRepository<Insumo, Long>{

    @Query(
            value = "SELECT * FROM insumo WHERE fecha_hora_baja_insumo IS NULL",
            nativeQuery = true
    )
    public List<Insumo> obtenerTodos();
}
