package com.example.inicial1.controllers;

import com.example.inicial1.dtos.AltaSucursalDTO;
import com.example.inicial1.entities.Sucursal;
import com.example.inicial1.services.SucursalServiceImpl;
import com.example.inicial1.services.SucursalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/sucursal")
public class SucursalController extends BaseControllerImpl<Sucursal, SucursalServiceImpl>{

    @Autowired
    SucursalServiceImpl sucursalService;

    //Buscar Todos con la fecha baja no nula
    @GetMapping("")
    public ResponseEntity<?> obtenerTodos() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(sucursalService.obtenerTodos());
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearSucursal(@RequestBody AltaSucursalDTO altaSucursalDTO) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body((sucursalService.crearSucursal(altaSucursalDTO)));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }


    //Borrar Cat
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> borrarSucursal(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(sucursalService.borrarSucursal(id));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }
}
