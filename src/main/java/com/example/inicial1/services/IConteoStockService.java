package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaConteoStockDTO;
import com.example.inicial1.dtos.UpdateControlStockDTO;
import com.example.inicial1.entities.ConteoStock;

import java.util.List;

public interface IConteoStockService extends BaseService<ConteoStock,Long>{
    public ConteoStock crearConteoStock(AltaConteoStockDTO altaConteoStockDTO) throws Exception;
    public List<ConteoStock> obtenerTodos() throws Exception;
    public ConteoStock update(Long id, UpdateControlStockDTO csdto) throws Exception;
}
