package com.example.inicial1.controllers;


import com.example.inicial1.entities.TipoUsuario;
import com.example.inicial1.services.TipoUsuarioServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/tipousuario")
public class TipoUsuarioController extends BaseControllerImpl<TipoUsuario, TipoUsuarioServiceImpl>{
}
