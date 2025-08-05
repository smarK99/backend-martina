package com.example.inicial1.controllers;


import com.example.inicial1.dtos.AltaTipoUsuarioDTO;
import com.example.inicial1.entities.TipoUsuario;
import com.example.inicial1.services.TipoUsuarioServiceImpl;
import com.example.inicial1.services.TipoUsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/tipo_usuario")
public class TipoUsuarioController extends BaseControllerImpl<TipoUsuario, TipoUsuarioServiceImpl>{

    @Autowired
    TipoUsuarioServiceImpl tipoUsuarioService;

    //Buscar Todos con la fecha baja no nula
    @GetMapping("")
    public ResponseEntity<?> obtenerTodos() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(tipoUsuarioService.obtenerTodos());
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }
    
    //Crear Tipo Usuario
    @PostMapping("/crear")
    public ResponseEntity<?> crearTipoUsuario(@RequestBody AltaTipoUsuarioDTO altaTipoUsuarioDTO) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body((tipoUsuarioService.crearTipoUsuario(altaTipoUsuarioDTO)));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }


    //Borrar Tipo Usuario
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> borrarTipoUsuario(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(tipoUsuarioService.borrarTipoUsuario(id));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }
}
