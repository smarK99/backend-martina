package com.example.inicial1.controllers;

import com.example.inicial1.entities.Insumo;
import com.example.inicial1.services.InsumoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/insumo")
public class InsumoController extends BaseControllerImpl<Insumo, InsumoServiceImpl>{
}
