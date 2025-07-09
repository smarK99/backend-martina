package com.example.inicial1.controllers;

import com.example.inicial1.dtos.ProductoInsumoDTO;
import com.example.inicial1.entities.ProductoInsumo;
import com.example.inicial1.services.ProductoInsumoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/producto_insumo")
public class ProductoInsumoController extends BaseControllerImpl<ProductoInsumo, ProductoInsumoServiceImpl>{

    @Autowired
    ProductoInsumoServiceImpl productoInsumoService;

    @PostMapping("/configurarPI")
    public ResponseEntity<?> configurarProductoInsumo(@RequestBody ProductoInsumoDTO pidto) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(productoInsumoService.configurarPI(pidto));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, por favor intente más tarde\"}");
        }
    }
}
