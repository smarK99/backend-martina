package com.example.inicial1.controllers;

import com.example.inicial1.entities.SucursalProducto;
import com.example.inicial1.services.SucursalProductoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/sucursalproducto")
public class SucursalProductoController extends BaseControllerImpl<SucursalProducto, SucursalProductoServiceImpl>{
}
