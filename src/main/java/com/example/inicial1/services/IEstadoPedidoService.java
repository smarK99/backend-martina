package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaCategoriaDTO;
import com.example.inicial1.dtos.AltaEstadoPedidoDTO;
import com.example.inicial1.entities.Categoria;
import com.example.inicial1.entities.EstadoPedido;

import java.util.List;

public interface IEstadoPedidoService extends BaseService<EstadoPedido,Long>{

    public List<EstadoPedido> obtenerTodos() throws Exception;
    public EstadoPedido crearEstadoPedido(AltaEstadoPedidoDTO altaEstadoPedidoDTO) throws Exception;
    public Boolean borrarEstadoPedido(Long id) throws Exception;
}
