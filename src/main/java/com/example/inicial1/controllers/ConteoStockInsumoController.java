package com.example.inicial1.controllers;

import com.example.inicial1.entities.ConteoStockInsumo;
import com.example.inicial1.services.ConteoStockInsumoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/conteo_stock_insumo")
public class ConteoStockInsumoController extends BaseControllerImpl<ConteoStockInsumo, ConteoStockInsumoServiceImpl>{
}
