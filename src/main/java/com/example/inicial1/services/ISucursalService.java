package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaSucursalDTO;
import com.example.inicial1.entities.Sucursal;

import java.util.List;

public interface ISucursalService extends BaseService<Sucursal,Long>{
    public List<Sucursal> obtenerTodos() throws Exception;
    public Sucursal crearSucursal(AltaSucursalDTO altaSucursalDTO) throws Exception;
    public Boolean borrarSucursal(Long id) throws Exception;
}
