package com.example.inicial1.controllers;

import com.example.inicial1.dtos.AltaUsuarioDTO;
import com.example.inicial1.dtos.UsuarioTUDTO;
import com.example.inicial1.entities.Usuario;
import com.example.inicial1.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/usuario")
public class UsuarioController extends BaseControllerImpl<Usuario, UsuarioServiceImpl>{

    @Autowired
    UsuarioServiceImpl usuarioService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@RequestBody AltaUsuarioDTO altaUsuarioDTO) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.crearUsuario(altaUsuarioDTO));
        }
        catch (Exception e) {
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }


    @PostMapping("/asignarRolUsuario")
    public ResponseEntity<?> asignarRolUsuario(@RequestBody UsuarioTUDTO utudto) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.asignarRolUsuario(utudto));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }

    //Revocar Rol usuario

    @DeleteMapping("/borrar/{idUsuario}")
    public ResponseEntity<?> borrarUsuario(@PathVariable Long idUsuario) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.borrarUsuario(idUsuario));
        }
        catch (Exception e){
            e.printStackTrace(); // Imprime el stack trace de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error, por favor intente más tarde. Detalle: " + e.getMessage() + "\"}");
        }
    }
}
