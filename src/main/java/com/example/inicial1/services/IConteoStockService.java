package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaConteoStockDTO;
import com.example.inicial1.entities.ConteoStock;

import java.util.List;

public interface IConteoStockService extends BaseService<ConteoStock,Long>{
    public ConteoStock crearConteoStock(AltaConteoStockDTO altaConteoStockDTO) throws Exception;
}
