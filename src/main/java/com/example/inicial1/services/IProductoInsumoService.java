package com.example.inicial1.services;

import com.example.inicial1.dtos.ProductoInsumoDTO;
import com.example.inicial1.entities.ProductoInsumo;

public interface IProductoInsumoService extends BaseService<ProductoInsumo,Long>{

    public ProductoInsumo configurarPI(ProductoInsumoDTO pidto) throws Exception;
}
