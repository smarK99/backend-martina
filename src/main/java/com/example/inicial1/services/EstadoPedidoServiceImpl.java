package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaEstadoPedidoDTO;
import com.example.inicial1.entities.EstadoPedido;
import com.example.inicial1.repositories.EstadoPedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class EstadoPedidoServiceImpl extends BaseServiceImpl<EstadoPedido,Long> implements IEstadoPedidoService{

    @Autowired
    EstadoPedidoRepository estadoPedidoRepository;

    @Transactional
    @Override
    public List<EstadoPedido> obtenerTodos() throws Exception {
        try {
            return estadoPedidoRepository.obtenerTodos();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    @Override
    public EstadoPedido crearEstadoPedido(AltaEstadoPedidoDTO altaEstadoPedidoDTO) throws Exception {

        try{
            EstadoPedido estadoPedido = EstadoPedido.builder()
                    .nombreEstadoPedido(altaEstadoPedidoDTO.getNombreEstadoPedido())
                    .descripcionEstadoPedido(altaEstadoPedidoDTO.getDescripcionEstadoPedido())
                    .fechaHoraAltaEstadoPedido(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .fechaHoraBajaEstadoPedido(null)
                    .build();

            return estadoPedidoRepository.save(estadoPedido);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Boolean borrarEstadoPedido(Long id) throws Exception {
        try{
            if(estadoPedidoRepository.existsById(id)){
                EstadoPedido estadoPedido = estadoPedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Estado Pedido no encontrado"));
                estadoPedido.setFechaHoraBajaEstadoPedido(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                estadoPedidoRepository.save(estadoPedido);
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
