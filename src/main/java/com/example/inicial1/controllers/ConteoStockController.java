package com.example.inicial1.controllers;

import com.example.inicial1.entities.ConteoStock;
import com.example.inicial1.services.ConteoStockServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/conteostock")
public class ConteoStockController extends BaseControllerImpl<ConteoStock, ConteoStockServiceImpl>{
}
