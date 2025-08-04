package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaEstadoRepartoDTO;
import com.example.inicial1.entities.EstadoReparto;
import com.example.inicial1.entities.EstadoReparto;
import com.example.inicial1.repositories.EstadoRepartoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class EstadoRepartoServiceImpl extends BaseServiceImpl<EstadoReparto,Long> implements IEstadoRepartoService{

    @Autowired
    EstadoRepartoRepository estadoRepartoRepository;

    @Transactional
    @Override
    public List<EstadoReparto> obtenerTodos() throws Exception {
        try {
            return estadoRepartoRepository.obtenerTodos();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    @Override
    public EstadoReparto crearEstadoReparto(AltaEstadoRepartoDTO altaEstadoRepartoDTO) throws Exception {

        try{
            EstadoReparto estadoReparto = EstadoReparto.builder()
                    .nombreEstadoReparto(altaEstadoRepartoDTO.getNombreEstadoReparto())
                    .descripcionEstadoReparto(altaEstadoRepartoDTO.getDescripcionEstadoReparto())
                    .fechaHoraAltaEstadoReparto(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .fechaHoraBajaEstadoReparto(null)
                    .build();

            return estadoRepartoRepository.save(estadoReparto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Boolean borrarEstadoReparto(Long id) throws Exception {
        try{
            if(estadoRepartoRepository.existsById(id)){
                EstadoReparto estadoReparto = estadoRepartoRepository.findById(id).orElseThrow(() -> new RuntimeException("Estado Reparto no encontrado"));
                estadoReparto.setFechaHoraBajaEstadoReparto(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                estadoRepartoRepository.save(estadoReparto);
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
