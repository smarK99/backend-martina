package com.example.inicial1.controllers;

import com.example.inicial1.entities.Usuario;
import com.example.inicial1.services.UsuarioServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/usuario")
public class UsuarioController extends BaseControllerImpl<Usuario, UsuarioServiceImpl>{

}
