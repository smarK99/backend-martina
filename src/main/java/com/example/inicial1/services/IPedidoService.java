package com.example.inicial1.services;

import com.example.inicial1.dtos.PedidoDTO;
import com.example.inicial1.dtos.PedidosPorSucursalDTO;
import com.example.inicial1.entities.DetallePedido;
import com.example.inicial1.entities.Pedido;

import java.util.List;

public interface IPedidoService extends BaseService<Pedido,Long>{

    public Pedido generarPedido(PedidoDTO pedidoDTO) throws Exception;

    public List<PedidosPorSucursalDTO> buscarPedidosPorSucursal(Long idSucursal) throws Exception;
}
