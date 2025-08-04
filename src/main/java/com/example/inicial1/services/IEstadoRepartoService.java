package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaEstadoRepartoDTO;
import com.example.inicial1.entities.EstadoReparto;

import java.util.List;

public interface IEstadoRepartoService extends BaseService<EstadoReparto,Long>{
    public List<EstadoReparto> obtenerTodos() throws Exception;
    public EstadoReparto crearEstadoReparto(AltaEstadoRepartoDTO altaEstadoRepartoDTO) throws Exception;
    public Boolean borrarEstadoReparto(Long id) throws Exception;
}
