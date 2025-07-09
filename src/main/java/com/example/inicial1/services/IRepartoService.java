package com.example.inicial1.services;

import com.example.inicial1.dtos.AgregarPedidosDTO;
import com.example.inicial1.dtos.RepartoDTO;
import com.example.inicial1.entities.Reparto;
import com.example.inicial1.repositories.RepartoRepository;

import java.util.List;

public interface IRepartoService extends BaseService<Reparto,Long>{

    public Reparto crearReparto(RepartoDTO repartoDTO) throws Exception;
    public Boolean agregarPedido(Long idReparto, List<AgregarPedidosDTO> pedidos) throws  Exception;
    public Boolean finalizarReparto(Long idReparto) throws Exception;
}
