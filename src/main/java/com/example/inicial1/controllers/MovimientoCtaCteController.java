package com.example.inicial1.controllers;

import com.example.inicial1.entities.MovimientoCtaCte;
import com.example.inicial1.services.MovimientoCtaCteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/movimientos")
public class MovimientoCtaCteController extends BaseControllerImpl<MovimientoCtaCte, MovimientoCtaCteServiceImpl> {

    @Autowired
    MovimientoCtaCteServiceImpl movimientoCtaCteService;

    //Obtiene historial de movimientos del cliente
    @GetMapping("/sucursal/{id}")
    public ResponseEntity<?> obtenerHistorial(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(movimientoCtaCteService.obtenerHistorial(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error al obtener historial.\"}");
        }
    }

    //Registrar Movimiento cta cte del cliente
    @PostMapping("/registrar_mov")
    public ResponseEntity<?> registrarMovimiento(@RequestBody MovimientoCtaCte nuevoMovimiento) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(movimientoCtaCteService.registrarMovimiento(nuevoMovimiento));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error al registrar movimiento.\"}");
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MovimientoCtaCte entity) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body("{\"error\":\"Operación denegada\"}");
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body("{\"error\":\"Operación denegada\"}");
    }
}