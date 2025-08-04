package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaSucursalDTO;
import com.example.inicial1.entities.Sucursal;
import com.example.inicial1.entities.Sucursal;
import com.example.inicial1.repositories.SucursalRepository;
import com.example.inicial1.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class SucursalServiceImpl extends BaseServiceImpl<Sucursal,Long> implements ISucursalService{

    @Autowired
    SucursalRepository sucursalRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Transactional
    @Override
    public List<Sucursal> obtenerTodos() throws Exception {
        try {
            return sucursalRepository.obtenerTodos();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Sucursal crearSucursal(AltaSucursalDTO altaSucursalDTO) throws Exception {

        try{
            Sucursal sucursal = Sucursal.builder()
                    .nombreSucursal(altaSucursalDTO.getNombreSucursal())
                    .descripcionSucursal(altaSucursalDTO.getDescripcionSucursal())
                    .direccionSucursal(altaSucursalDTO.getDireccionSucursal())
                    .fechaHoraAltaSucursal(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .fechaHoraBajaSucursal(null)
                    .sucursalProductoList(new ArrayList<>())
                    .usuario(usuarioRepository.findById(altaSucursalDTO.getIdUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado")))
                    .build();

            return sucursalRepository.save(sucursal);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Boolean borrarSucursal(Long id) throws Exception {
        try{
            if(sucursalRepository.existsById(id)){
                Sucursal sucursal = sucursalRepository.findById(id).orElseThrow(() -> new RuntimeException("Sucursal no encontrado"));
                sucursal.setFechaHoraBajaSucursal(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                sucursalRepository.save(sucursal);
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
