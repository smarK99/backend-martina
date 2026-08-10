package com.example.inicial1.controllers;

import com.example.inicial1.dtos.*;
import com.example.inicial1.entities.Reparto;
import com.example.inicial1.services.RepartoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/agregar_pedidos/{idReparto}")
    public ResponseEntity<?> agregarPedido(@PathVariable Long idReparto, @RequestBody List<AgregarPedidosDTO> pedidos){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(repartoService.agregarPedido(idReparto, pedidos));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/entregar_pedido")
    public ResponseEntity<?> entregarPedido(@RequestBody EntregarPedidoDTO entregarPedidoDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(repartoService.entregarPedido(entregarPedidoDTO));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/cargar_gasto")
    public ResponseEntity<?> cargarGasto(@RequestBody CargarGastoDTO cargarGastoDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(repartoService.cargarGasto(cargarGastoDTO));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/finalizar")
    public ResponseEntity<?> finalizarReparto(@RequestBody FinalizarRepartoDTO finalizarRepartoDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(repartoService.finalizarReparto(finalizarRepartoDTO));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/realizar_rendicion")
    public ResponseEntity<?> realizarRendicion(@RequestBody RealizarRendicionDTO realizarRendicionDTO) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(repartoService.realizarRendicion(realizarRendicionDTO));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    // ==========================================
    // ENDPOINT PARA PAGINACIÓN Y BÚSQUEDA (CON ESTADO, FECHA Y TÉRMINO)
    // ==========================================
    @GetMapping("/busqueda-paginada")
    public ResponseEntity<?> buscarPaginadoYFiltrado(
            @RequestParam(required = false, defaultValue = "") String termino,
            @RequestParam(defaultValue = "0") Integer idRepartidor,
            @RequestParam(required = false, defaultValue = "") String fecha,
            @RequestParam(defaultValue = "0") Long idEstado, // <-- RECIBE EL ESTADO
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        try {
            // SE LO PASAMOS AL SERVICIO
            Page<Reparto> resultados = repartoService.buscarPaginadoYFiltrado(termino, idRepartidor, fecha, idEstado, page, size);
            return ResponseEntity.status(HttpStatus.OK).body(resultados);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }
}