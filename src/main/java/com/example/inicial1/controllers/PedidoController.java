package com.example.inicial1.controllers;

import com.example.inicial1.dtos.PedidoDTO;
import com.example.inicial1.entities.DetallePedido;
import com.example.inicial1.entities.Pedido;
import com.example.inicial1.services.IPedidoService;
import com.example.inicial1.services.PedidoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/pedido")
public class PedidoController extends BaseControllerImpl<Pedido, PedidoServiceImpl>{

    @Autowired
    PedidoServiceImpl pedidoService;

    @PostMapping("/realizar_pedido")
    public ResponseEntity<?> generarPedido(@RequestBody PedidoDTO pedidoDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(pedidoService.generarPedido(pedidoDTO));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    //Buscar historial de pedidos por sucursal
    @GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<?> buscarPedidosPorSucursal(@PathVariable Long idSucursal) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(pedidoService.buscarPedidosPorSucursal(idSucursal));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    //Cancelar Pedido
}
