package com.example.inicial1.controllers;

import com.example.inicial1.dtos.AltaCategoriaDTO;
import com.example.inicial1.dtos.AltaEstadoPedidoDTO;
import com.example.inicial1.entities.EstadoPedido;
import com.example.inicial1.services.EstadoPedidoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/estadopedido")
public class EstadoPedidoController extends BaseControllerImpl<EstadoPedido, EstadoPedidoServiceImpl>{

    @Autowired
    EstadoPedidoServiceImpl estadoPedidoService;

    //ABMS

    //Buscar Todos con la fecha baja no nula
    @GetMapping("")
    public ResponseEntity<?> obtenerTodos() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(estadoPedidoService.obtenerTodos());
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearEstadoPedido(@RequestBody AltaEstadoPedidoDTO altaEstadoPedidoDTO) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body((estadoPedidoService.crearEstadoPedido(altaEstadoPedidoDTO)));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    //Borrar EP
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> borrarEstadoPedido(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(estadoPedidoService.borrarEstadoPedido(id));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }
}
