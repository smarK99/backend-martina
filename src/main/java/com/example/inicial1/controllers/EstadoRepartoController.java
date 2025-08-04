package com.example.inicial1.controllers;

import com.example.inicial1.dtos.AltaEstadoRepartoDTO;
import com.example.inicial1.entities.EstadoReparto;
import com.example.inicial1.services.EstadoRepartoServiceImpl;
import com.example.inicial1.services.EstadoRepartoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/estadoreparto")
public class EstadoRepartoController extends BaseControllerImpl<EstadoReparto, EstadoRepartoServiceImpl>{

    @Autowired
    EstadoRepartoServiceImpl estadoRepartoService;

    //ABMS

    //Buscar Todos con la fecha baja no nula
    @GetMapping("")
    public ResponseEntity<?> obtenerTodos() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(estadoRepartoService.obtenerTodos());
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearEstadoReparto(@RequestBody AltaEstadoRepartoDTO altaEstadoRepartoDTO) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body((estadoRepartoService.crearEstadoReparto(altaEstadoRepartoDTO)));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    //Borrar EP
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> borrarEstadoReparto(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(estadoRepartoService.borrarEstadoReparto(id));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }
    
    
}
