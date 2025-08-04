package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaCategoriaDTO;
import com.example.inicial1.dtos.RealizarRendicionDTO;
import com.example.inicial1.entities.Categoria;
import com.example.inicial1.entities.Rendicion;
import com.example.inicial1.entities.Reparto;
import com.example.inicial1.repositories.RendicionRepository;
import com.example.inicial1.repositories.RepartoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class RendicionServiceImpl extends BaseServiceImpl<Rendicion,Long> implements IRendicionService{
}
