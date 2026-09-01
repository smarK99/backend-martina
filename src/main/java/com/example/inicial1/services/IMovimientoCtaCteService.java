package com.example.inicial1.services;

import com.example.inicial1.entities.MovimientoCtaCte;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IMovimientoCtaCteService extends BaseService<MovimientoCtaCte,Long>{
    List<MovimientoCtaCte> obtenerHistorial(Long idSucursal) throws Exception;
    MovimientoCtaCte registrarMovimiento(MovimientoCtaCte nuevoMovimiento) throws Exception;
}
