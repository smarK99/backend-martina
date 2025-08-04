package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaProductoDTO;
import com.example.inicial1.entities.Producto;

import java.util.List;

public interface IProductoService extends BaseService<Producto,Long>{
    public List<Producto> obtenerTodos() throws Exception;
    public Producto crearProducto(AltaProductoDTO altaProductoDTO) throws Exception;
    public Boolean borrarProducto(Long id) throws Exception;
}
