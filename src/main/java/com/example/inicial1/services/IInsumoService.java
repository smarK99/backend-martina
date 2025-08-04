package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaInsumoDTO;
import com.example.inicial1.entities.Insumo;

import java.util.List;

public interface IInsumoService extends BaseService<Insumo,Long>{

    public List<Insumo> obtenerTodos() throws Exception;
    public Insumo crearInsumo(AltaInsumoDTO altaInsumoDTO) throws Exception;
    public Boolean borrarInsumo(Long id) throws Exception;
}
