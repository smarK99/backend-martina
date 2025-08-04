package com.example.inicial1.repositories;

import com.example.inicial1.entities.EstadoReparto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoRepartoRepository extends BaseRepository<EstadoReparto, Long>{

    @Query(
            value = "SELECT * FROM estado_reparto WHERE fecha_hora_baja_estado_reparto IS NULL",
            nativeQuery = true
    )
    public List<EstadoReparto> obtenerTodos();
}
