package com.example.inicial1.services;

import com.example.inicial1.dtos.PreciosPorSucursalDTO;
import com.example.inicial1.entities.SucursalProducto;

import java.util.List;

public interface ISucursalProductoService extends BaseService<SucursalProducto,Long>{

    public List<PreciosPorSucursalDTO> obtenerPreciosPorSucursal(Long idSucursal) throws Exception;
}
