package com.example.inicial1.controllers;

import com.example.inicial1.dtos.AltaProductoDTO;
import com.example.inicial1.entities.Producto;
import com.example.inicial1.services.ProductoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/producto")
public class ProductoController extends BaseControllerImpl<Producto, ProductoServiceImpl>{
    
    //ABMS
    @Autowired
    ProductoServiceImpl productoService;

    //Buscar Todos con la fecha baja no nula
    @GetMapping("")
    public ResponseEntity<?> obtenerTodos() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(productoService.obtenerTodos());
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearProducto(@RequestBody AltaProductoDTO altaProductoDTO) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body((productoService.crearProducto(altaProductoDTO)));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }


    //Borrar Cat
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> borrarProducto(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(productoService.borrarProducto(id));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }
}
