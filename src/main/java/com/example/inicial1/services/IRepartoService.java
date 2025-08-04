package com.example.inicial1.services;

import com.example.inicial1.dtos.*;
import com.example.inicial1.entities.Gasto;
import com.example.inicial1.entities.Rendicion;
import com.example.inicial1.entities.Reparto;

import java.util.List;

public interface IRepartoService extends BaseService<Reparto,Long>{

    public Reparto crearReparto(RepartoDTO repartoDTO) throws Exception;
    public Boolean agregarPedido(Long idReparto, List<AgregarPedidosDTO> pedidos) throws  Exception;
    public Boolean entregarPedido(EntregarPedidoDTO entregarPedidoDTO) throws  Exception;
    public Gasto cargarGasto(CargarGastoDTO cargarGastoDTO) throws Exception;
    public Boolean finalizarReparto(FinalizarRepartoDTO finalizarRepartoDTO) throws Exception;
    public Rendicion realizarRendicion(RealizarRendicionDTO realizarRendicionDTO) throws Exception;

}
