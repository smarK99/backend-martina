package com.example.inicial1.controllers;

import com.example.inicial1.entities.Categoria;
import com.example.inicial1.entities.Gasto;
import com.example.inicial1.services.CategoriaServiceImpl;
import com.example.inicial1.services.GastoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/gasto")
public class GastoController extends BaseControllerImpl<Gasto, GastoServiceImpl>{
}
