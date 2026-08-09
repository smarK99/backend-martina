package com.example.inicial1.services;

import com.example.inicial1.dtos.*;
import com.example.inicial1.entities.*;
import com.example.inicial1.enums.MetodoPago;
import com.example.inicial1.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    EstadoPedidoRepository estadoPedidoRepository;

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
                        .rendicion(new Rendicion(0D, 0D, 0D, null))
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
        try{
            Reparto reparto = repartoRepository.findById(idReparto).orElseThrow(() -> new RuntimeException("El reparto no existe"));
            if(!reparto.getEstadoReparto().getNombreEstadoReparto().equals("FINALIZADO")){

                for(AgregarPedidosDTO p : pedidos){
                    reparto.getPedidosList().add(pedidoRepository.findById(p.getIdPedido()).orElseThrow(() -> new RuntimeException("El pedido no existe")));
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
    public Boolean entregarPedido(EntregarPedidoDTO entregarPedidoDTO) throws Exception {
        try{
            Reparto r = repartoRepository.findById(entregarPedidoDTO.getIdReparto()).orElseThrow(() -> new RuntimeException("Reparto no encontrado"));

            for(Pedido p : r.getPedidosList()){
                if(p.getId().equals(entregarPedidoDTO.getIdPedido())){
                    p.setEstadoPedido(estadoPedidoRepository.findById(4L).orElseThrow(() -> new RuntimeException("EstadoPedido 'ENTREGADO' no encontrado")));
                    p.setMetodoPago(entregarPedidoDTO.getMetodoPago());
                    if(entregarPedidoDTO.getMetodoPago().equals(MetodoPago.EFECTIVO)){
                        Double montoRecaudado = r.getRendicion().getMontoRecaudado() + p.getImporteTotalPedido();
                        r.getRendicion().setMontoRecaudado(montoRecaudado);
                    }
                    break;
                }
            }
            repartoRepository.save(r);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Gasto cargarGasto(CargarGastoDTO cargarGastoDTO) throws Exception {
        try{
            Reparto reparto = repartoRepository.findById(cargarGastoDTO.getIdReparto()).orElseThrow(()-> new RuntimeException("El reparto no existe"));
            List<Gasto> gastos = reparto.getRendicion().getGastos();
            Gasto gastoNuevo = new Gasto(cargarGastoDTO.getNombreGasto(), cargarGastoDTO.getMontoGasto());
            gastos.add(gastoNuevo);

            repartoRepository.save(reparto);
            return gastoNuevo;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Boolean finalizarReparto(FinalizarRepartoDTO finalizarRepartoDTO) throws Exception {
        try{
            if(repartoRepository.existsById(finalizarRepartoDTO.getIdReparto())){
                Reparto repartoFinalizado = repartoRepository.findById(finalizarRepartoDTO.getIdReparto()).orElseThrow(()-> new RuntimeException("El reparto no existe"));

                repartoFinalizado.getRendicion().setMontoRecaudado(finalizarRepartoDTO.getMontoRecaudado());
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

    @Transactional
    @Override
    public Rendicion realizarRendicion(RealizarRendicionDTO realizarRendicionDTO) throws Exception {
        try{
            Reparto r1 = repartoRepository.findById(realizarRendicionDTO.getIdReparto()).orElseThrow(() -> new RuntimeException("Reparto No encontrado"));
            Rendicion rendicion = r1.getRendicion();
            rendicion.setMontoRendido(realizarRendicionDTO.getMontoRendido());

            Double gastoTotal = 0D;

            for(Gasto gasto : rendicion.getGastos()){
                gastoTotal += gasto.getMontoGasto();
            }

            if( rendicion.getMontoRendido().equals(rendicion.getMontoRecaudado() - gastoTotal)){
                rendicion.setDiferencia(0D);
                return rendicion;
            }else {
                rendicion.setDiferencia((rendicion.getMontoRecaudado() - gastoTotal) - rendicion.getMontoRendido());
                return  rendicion;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    // ==========================================
    // MÉTODO PARA PAGINACIÓN Y BÚSQUEDA (CON ID ESTADO)
    // ==========================================
    public Page<Reparto> buscarPaginadoYFiltrado(String termino, Integer idRepartidor, String fecha, Long idEstado, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repartoRepository.buscarPaginadoYFiltrado(termino, idRepartidor, idEstado, fecha, pageable);
    }
}