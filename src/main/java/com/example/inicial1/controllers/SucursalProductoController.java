package com.example.inicial1.controllers;

import com.example.inicial1.dtos.SucursalProductoDTO;
import com.example.inicial1.entities.SucursalProducto;
import com.example.inicial1.services.SucursalProductoServiceImpl;
import com.example.inicial1.services.SucursalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/sucursal_producto")
public class SucursalProductoController extends BaseControllerImpl<SucursalProducto, SucursalProductoServiceImpl>{

    @Autowired
    SucursalProductoServiceImpl sucursalProductoService;

    //Mostrar precios x sucursal
    @GetMapping("/precios/{idSucursal}")
    public ResponseEntity<?> obtenerPreciosPorSucursal(@PathVariable Long idSucursal){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(sucursalProductoService.obtenerPreciosPorSucursal(idSucursal));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error, por favor intente más tarde\"}");
        }
    }

    @PostMapping("/configurar_precio")
    public ResponseEntity<?> configurarPrecioSP(@RequestBody SucursalProductoDTO spdto) throws Exception{
        try{
            return ResponseEntity.status(HttpStatus.OK).
                    body(sucursalProductoService.configurarPrecioSP(spdto));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error, por favor intente más tarde\"}");
        }
    }
}
