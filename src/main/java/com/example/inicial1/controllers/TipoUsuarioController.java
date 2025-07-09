package com.example.inicial1.controllers;


import com.example.inicial1.entities.TipoUsuario;
import com.example.inicial1.services.TipoUsuarioServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/tipo_usuario")
public class TipoUsuarioController extends BaseControllerImpl<TipoUsuario, TipoUsuarioServiceImpl>{
}
