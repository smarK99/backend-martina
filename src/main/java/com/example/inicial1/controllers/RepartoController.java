package com.example.inicial1.controllers;

import com.example.inicial1.entities.Reparto;
import com.example.inicial1.services.RepartoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/reparto")
public class RepartoController extends BaseControllerImpl<Reparto, RepartoServiceImpl>{
}
