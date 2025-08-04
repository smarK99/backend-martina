package com.example.inicial1.repositories;

import com.example.inicial1.entities.Sucursal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SucursalRepository extends BaseRepository<Sucursal, Long>{

    @Query(
            value = "SELECT * FROM sucursal WHERE fecha_hora_baja_sucursal IS NULL",
            nativeQuery = true
    )
    public List<Sucursal> obtenerTodos();
}
