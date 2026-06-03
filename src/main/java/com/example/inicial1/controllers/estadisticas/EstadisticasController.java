package com.example.inicial1.controllers.estadisticas;

import com.example.inicial1.services.EstadisticasServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/estadisticas")
public class EstadisticasController {

    @Autowired
    EstadisticasServiceImpl estadisticasService;

    //Total ventas por sucursal (puede ser en los ultimos meses o desde siempre)
    @GetMapping("/vtas_por_sucursal")
    public ResponseEntity<?> obtenerTotalVtasPorSucursal() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(estadisticasService.obtenerTotalVtasPorSucursal());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    //Producto mas vendido
    @GetMapping("/prods_mas_vendidos")
    public ResponseEntity<?> obtenerProdsMasVendidos() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(estadisticasService.obtenerProdsMasVendidos());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    //Total facturado en los ultimos 30 dias
    @GetMapping("/recaudacion_30dias")
    public ResponseEntity<?> obtenerRecaudacionUltimos30Dias() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(estadisticasService.obtenerRecaudacionUltimos30Dias());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    //Total facturado en los ultimos 7 dias
    @GetMapping("/recaudacion_7dias")
    public ResponseEntity<?> obtenerRecaudacionUltimos7Dias() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(estadisticasService.obtenerRecaudacionUltimos7Dias());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    //Total de ventas desde el inicio
}
