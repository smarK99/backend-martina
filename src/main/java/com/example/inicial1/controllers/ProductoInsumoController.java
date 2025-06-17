package com.example.inicial1.controllers;

import com.example.inicial1.entities.ProductoInsumo;
import com.example.inicial1.services.ProductoInsumoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/productoinsumo")
public class ProductoInsumoController extends BaseControllerImpl<ProductoInsumo, ProductoInsumoServiceImpl>{
}
