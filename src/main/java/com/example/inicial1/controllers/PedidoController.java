package com.example.inicial1.controllers;

import com.example.inicial1.dtos.PedidoDTO;
import com.example.inicial1.entities.Pedido;
import com.example.inicial1.services.PedidoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/pedido")
public class PedidoController extends BaseControllerImpl<Pedido, PedidoServiceImpl>{

    @Autowired
    PedidoServiceImpl pedidoService;

    // Traer todos los pedidos paginados
    @GetMapping("/todos-paginados")
    public ResponseEntity<?> obtenerTodosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return ResponseEntity.status(HttpStatus.OK).body(pedidoService.obtenerTodosPaginados(pageable));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    // Buscar pedidos por sucursal paginados
    @GetMapping("/sucursal/{idSucursal}/paged")
    public ResponseEntity<?> buscarPedidosPorSucursalPaginados(
            @PathVariable Long idSucursal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return ResponseEntity.status(HttpStatus.OK).body(pedidoService.buscarPedidosPorSucursalPaginados(idSucursal, pageable));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/realizar_pedido")
    public ResponseEntity<?> generarPedido(@RequestBody PedidoDTO pedidoDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(pedidoService.generarPedido(pedidoDTO));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/disponibles_reparto")
    public ResponseEntity<?> obtenerPedidosDisponiblesParaReparto(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(pedidoService.obtenerPedidosDisponiblesParaReparto());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<?> cancelarPedido(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(pedidoService.cancelarPedido(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al cancelar el pedido. Detalle: " + e.getMessage() + "\"}");
        }
    }


    // ==========================================
    // MÉTODO ACTUALIZADO: Endpoint para la barra de búsqueda combinada (Sucursal, Fecha, Estado y Texto)
    // ==========================================
    @GetMapping("/busqueda-paginada")
    public ResponseEntity<?> buscarPaginadoYFiltrado(
            @RequestParam(required = false, defaultValue = "") String termino,
            @RequestParam(required = false, defaultValue = "0") Long idSucursal,
            @RequestParam(required = false, defaultValue = "") String fecha,
            @RequestParam(defaultValue = "0") Long idEstado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return ResponseEntity.status(HttpStatus.OK).body(pedidoService.buscarPaginadoYFiltrado(termino, idSucursal, fecha, idEstado, pageable));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }
}