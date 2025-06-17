package com.example.inicial1.controllers;

import com.example.inicial1.entities.EstadoReparto;
import com.example.inicial1.services.EstadoRepartoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/estadoreparto")
public class EstadoRepartoController extends BaseControllerImpl<EstadoReparto, EstadoRepartoServiceImpl>{
}
