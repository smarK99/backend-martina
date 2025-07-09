package com.example.inicial1.controllers;

import com.example.inicial1.entities.Sucursal;
import com.example.inicial1.services.SucursalServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/sucursal")
public class SucursalController extends BaseControllerImpl<Sucursal, SucursalServiceImpl>{
}
