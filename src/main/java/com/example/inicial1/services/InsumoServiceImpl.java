package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaInsumoDTO;
import com.example.inicial1.entities.Insumo;
import com.example.inicial1.entities.Insumo;
import com.example.inicial1.repositories.InsumoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class InsumoServiceImpl extends BaseServiceImpl<Insumo,Long> implements IInsumoService{

    @Autowired
    InsumoRepository insumoRepository;


    @Transactional
    @Override
    public List<Insumo> obtenerTodos() throws Exception {
        try {
            return insumoRepository.obtenerTodos();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Insumo crearInsumo(AltaInsumoDTO altaInsumoDTO) throws Exception {

        try{
            Insumo insumo = Insumo.builder()
                    .nombreInsumo(altaInsumoDTO.getNombreInsumo())
                    .descripcionInsumo(altaInsumoDTO.getDescripcionInsumo())
                    .precioCompraInsumo(altaInsumoDTO.getPrecioCompraInsumo())
                    .fechaHoraAltaInsumo(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .fechaHoraBajaInsumo(null)
                    .build();

            return insumoRepository.save(insumo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Boolean borrarInsumo(Long id) throws Exception {
        try{
            if(insumoRepository.existsById(id)){
                Insumo insumo = insumoRepository.findById(id).orElseThrow(() -> new RuntimeException("Insumo no encontrado"));
                insumo.setFechaHoraBajaInsumo(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                insumoRepository.save(insumo);
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
