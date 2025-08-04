package com.example.inicial1.controllers;

import com.example.inicial1.dtos.AltaCategoriaDTO;
import com.example.inicial1.entities.Categoria;
import com.example.inicial1.services.CategoriaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/categoria")
public class CategoriaController extends BaseControllerImpl<Categoria, CategoriaServiceImpl>{

    @Autowired
    CategoriaServiceImpl categoriaService;

    //Buscar Todos con la fecha baja no nula
    @GetMapping("")
    public ResponseEntity<?> obtenerTodos() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(categoriaService.obtenerTodos());
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearCategoria(@RequestBody AltaCategoriaDTO altaCategoriaDTO) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body((categoriaService.crearCategoria(altaCategoriaDTO)));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }


    //Borrar Cat
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> borrarCategoria(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(categoriaService.borrarCategoria(id));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }


}
