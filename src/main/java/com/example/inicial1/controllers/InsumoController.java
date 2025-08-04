package com.example.inicial1.controllers;

import com.example.inicial1.dtos.AltaCategoriaDTO;
import com.example.inicial1.dtos.AltaInsumoDTO;
import com.example.inicial1.entities.Insumo;
import com.example.inicial1.services.InsumoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/insumo")
public class InsumoController extends BaseControllerImpl<Insumo, InsumoServiceImpl>{

    @Autowired
    InsumoServiceImpl insumoService;

    //ABMS

    //Buscar Todos con la fecha baja no nula
    @GetMapping("")
    public ResponseEntity<?> obtenerTodos() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(insumoService.obtenerTodos());
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> creaInsumo(@RequestBody AltaInsumoDTO altaInsumoDTO) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body((insumoService.crearInsumo(altaInsumoDTO)));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    //Borrar Insumo
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> borrarInsumo(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(insumoService.borrarInsumo(id));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }
}
