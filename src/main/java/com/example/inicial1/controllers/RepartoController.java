package com.example.inicial1.controllers;

import com.example.inicial1.dtos.AgregarPedidosDTO;
import com.example.inicial1.dtos.PedidoDTO;
import com.example.inicial1.dtos.RepartoDTO;
import com.example.inicial1.entities.Reparto;
import com.example.inicial1.services.RepartoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/reparto")
public class RepartoController extends BaseControllerImpl<Reparto, RepartoServiceImpl>{

    @Autowired
    RepartoServiceImpl repartoService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearReparto(@RequestBody RepartoDTO repartoDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(repartoService.crearReparto(repartoDTO));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/agregar_pedidos/{idReparto}")
    public ResponseEntity<?> agregarPedido(@PathVariable Long idReparto, @RequestBody List<AgregarPedidosDTO> pedidos){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(repartoService.agregarPedido(idReparto, pedidos));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/finalizar/{idReparto}")
    public ResponseEntity<?> finalizarReparto(@PathVariable Long idReparto){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(repartoService.finalizarReparto(idReparto));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

}
