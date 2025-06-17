package com.example.inicial1.controllers;

import com.example.inicial1.entities.Pedido;
import com.example.inicial1.services.PedidoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/pedido")
public class PedidoController extends BaseControllerImpl<Pedido, PedidoServiceImpl>{
}
