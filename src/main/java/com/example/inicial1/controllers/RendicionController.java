package com.example.inicial1.controllers;

import com.example.inicial1.dtos.AltaCategoriaDTO;
import com.example.inicial1.dtos.RealizarRendicionDTO;
import com.example.inicial1.entities.Categoria;
import com.example.inicial1.entities.Rendicion;
import com.example.inicial1.services.CategoriaServiceImpl;
import com.example.inicial1.services.RendicionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/rendicion")
public class RendicionController extends BaseControllerImpl<Rendicion, RendicionServiceImpl>{
}
