package com.example.inicial1.controllers;

import com.example.inicial1.dtos.AltaCategoriaDTO;
import com.example.inicial1.dtos.AltaConteoStockDTO;
import com.example.inicial1.dtos.UpdateControlStockDTO;
import com.example.inicial1.entities.ConteoStock;
import com.example.inicial1.services.ConteoStockServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/conteostock")
public class ConteoStockController extends BaseControllerImpl<ConteoStock, ConteoStockServiceImpl>{

    @Autowired
    ConteoStockServiceImpl conteoStockService;

    @GetMapping("")
    public ResponseEntity<?> obtenerTodos() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(conteoStockService.obtenerTodos());
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearConteoStock(@RequestBody AltaConteoStockDTO altaConteoStockDTO) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body((conteoStockService.crearConteoStock(altaConteoStockDTO)));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body((conteoStockService.delete(id)));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateControlStockDTO csdto){
        try{
            return ResponseEntity.status(HttpStatus.OK).body((conteoStockService.update(id, csdto)));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }
}
