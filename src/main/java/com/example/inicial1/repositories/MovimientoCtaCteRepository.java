package com.example.inicial1.repositories;

import com.example.inicial1.entities.MovimientoCtaCte;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoCtaCteRepository extends BaseRepository<MovimientoCtaCte, Long> {

    // Trae el historial completo
    @Query("SELECT m FROM MovimientoCtaCte m WHERE m.sucursal.id = :idSucursal ORDER BY m.fechaHora DESC")
    List<MovimientoCtaCte> obtenerHistorialPorSucursal(@Param("idSucursal") Long idSucursal);

    // Trae el último saldo registrado (Usamos Pageable desde el Service para que SQL solo devuelva 1 fila)
    @Query("SELECT m FROM MovimientoCtaCte m WHERE m.sucursal.id = :idSucursal ORDER BY m.fechaHora DESC")
    List<MovimientoCtaCte> obtenerUltimoMovimiento(@Param("idSucursal") Long idSucursal, Pageable pageable);
}
