package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaTipoUsuarioDTO;
import com.example.inicial1.entities.TipoUsuario;
import com.example.inicial1.repositories.TipoUsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TipoUsuarioServiceImpl extends BaseServiceImpl<TipoUsuario,Long> implements ITipoUsuarioService{


    @Autowired
    TipoUsuarioRepository tipoUsuarioRepository;

    @Transactional
    @Override
    public List<TipoUsuario> obtenerTodos() throws Exception {
        try {
            return tipoUsuarioRepository.obtenerTodos();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    @Override
    public TipoUsuario crearTipoUsuario(AltaTipoUsuarioDTO altaTipoUsuarioDTO) throws Exception {

        try{
            TipoUsuario tipoUsuario = TipoUsuario.builder()
                    .nombreTipoUsuario(altaTipoUsuarioDTO.getNombreTipoUsuario())
                    .descripcionTipoUsuario(altaTipoUsuarioDTO.getDescripcionTipoUsuario())
                    .fechaHoraInicioVigenciaTipoUsuario(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .fechaHoraFinVigenciaTipoUsuario(null)
                    .build();

            return tipoUsuarioRepository.save(tipoUsuario);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Boolean borrarTipoUsuario(Long id) throws Exception {
        try{
            if(tipoUsuarioRepository.existsById(id)){
                TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("TipoUsuario no encontrado"));
                tipoUsuario.setFechaHoraFinVigenciaTipoUsuario(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                tipoUsuarioRepository.save(tipoUsuario);
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
