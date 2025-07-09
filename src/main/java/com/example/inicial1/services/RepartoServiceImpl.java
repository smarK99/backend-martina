package com.example.inicial1.services;

import com.example.inicial1.dtos.AgregarPedidosDTO;
import com.example.inicial1.dtos.RepartoDTO;
import com.example.inicial1.entities.EstadoReparto;
import com.example.inicial1.entities.Reparto;
import com.example.inicial1.repositories.EstadoRepartoRepository;
import com.example.inicial1.repositories.PedidoRepository;
import com.example.inicial1.repositories.RepartoRepository;
import com.example.inicial1.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RepartoServiceImpl extends BaseServiceImpl<Reparto,Long> implements IRepartoService{

    @Autowired
    EstadoRepartoRepository estadoRepartoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RepartoRepository repartoRepository;

    @Autowired
    PedidoRepository pedidoRepository;


    @Transactional
    @Override
    public Reparto crearReparto(RepartoDTO repartoDTO) throws Exception {

        try{
            if(estadoRepartoRepository.existsById(1L) && usuarioRepository.existsById(repartoDTO.getIdUsuario())){
                Reparto reparto = Reparto.builder()
                        .nombreReparto(repartoDTO.getNombreReparto())
                        .descripcionReparto(repartoDTO.getDescripcionReparto())
                        .fechaHoraInicioReparto(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                        .fechaHoraFinReparto(null)
                        .estadoReparto(estadoRepartoRepository.findById(1L).orElseThrow(() -> new RuntimeException("EstadoReparto 'Creado' no encontrado")))
                        .usuario(usuarioRepository.findById(repartoDTO.getIdUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado")))
                        .pedidosList(new ArrayList<>())
                        .build();

                return repartoRepository.save(reparto);
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Boolean agregarPedido(Long idReparto, List<AgregarPedidosDTO> pedidos) throws Exception {
        //Validar que el pedido no este asignado a otro reparto
        //Validar que el reparto no este finalizado
        try{
            if(repartoRepository.existsById(idReparto)){

                Reparto reparto = repartoRepository.findById(idReparto).orElseThrow(() -> new RuntimeException("El reparto no existe"));

                for(AgregarPedidosDTO p : pedidos){
                    //Chequear si existe el pedido
                    if (pedidoRepository.existsById(p.getIdPedido())){
                        reparto.getPedidosList().add(pedidoRepository.findById(p.getIdPedido()).orElseThrow(() -> new RuntimeException("El pedido no existe")));
                    }
                }
                repartoRepository.save(reparto);
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    @Transactional
    @Override
    public Boolean finalizarReparto(Long idReparto) throws Exception {
        try{
            if(repartoRepository.existsById(idReparto)){
                //Buscamos la instancia reparto correspondiente al id recibido
                Reparto repartoFinalizado = repartoRepository.findById(idReparto).orElseThrow(()-> new RuntimeException("El reparto no existe"));

                //Le seteamos el estado reparto finalizado (id 2)
                repartoFinalizado.setEstadoReparto(estadoRepartoRepository.findById(2L).orElseThrow(()-> new RuntimeException("El EstadoReparto no existe")));
                repartoFinalizado.setFechaHoraFinReparto(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

                return true;
            }else{
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }
}
