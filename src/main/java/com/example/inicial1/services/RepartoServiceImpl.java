package com.example.inicial1.services;

import com.example.inicial1.dtos.*;
import com.example.inicial1.entities.*;
import com.example.inicial1.enums.MetodoPago;
import com.example.inicial1.repositories.*;
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

    @Autowired
    EstadoPedidoRepository estadoPedidoRepository;

    //MOSTRAR PEDIDOS DEL REPARTO


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

    //MARCAR PEDIDO DEL REPARTO COMO ENTREGADO Y SU METODO DE PAGO
    @Transactional
    @Override
    public Boolean entregarPedido(EntregarPedidoDTO entregarPedidoDTO) throws Exception {
        try{
            //Buscamos el reparto actual
            Reparto r = repartoRepository.findById(entregarPedidoDTO.getIdReparto()).orElseThrow(() -> new RuntimeException("Reparto no encontrado"));

            //Buscamos ese pedido del reparto que se va a marcar como entregado y le seteamos el metodo de pago
            for(Pedido p : r.getPedidosList()){
                if(p.getId().equals(entregarPedidoDTO.getIdPedido())){
                    p.setEstadoPedido(estadoPedidoRepository.findById(4L).orElseThrow(() -> new RuntimeException("EstadoPedido 'ENTREGADO' no encontrado")));
                    p.setMetodoPago(entregarPedidoDTO.getMetodoPago());
                    //Se van sumando los pedidos cobrados en efectivo
                    if(entregarPedidoDTO.getMetodoPago().equals(MetodoPago.EFECTIVO)){
                        Double montoRecaudado = r.getRendicion().getMontoRecaudado() + p.getImporteTotalPedido();
                        r.getRendicion().setMontoRecaudado(montoRecaudado);
                    }
                    break;
                }
            }
            //Actualizo el reparto
            repartoRepository.save(r);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    //C.U CARGAR GASTOS
    @Transactional
    @Override
    public Gasto cargarGasto(CargarGastoDTO cargarGastoDTO) throws Exception {
        try{
            //Buscar el reparto
            Reparto reparto = repartoRepository.findById(cargarGastoDTO.getIdReparto()).orElseThrow(()-> new RuntimeException("El reparto no existe"));
            //Obtener la lista de gastos
            List<Gasto> gastos = reparto.getRendicion().getGastos();
            //Agregar el nuevo gasto
            Gasto gastoNuevo = new Gasto(cargarGastoDTO.getNombreGasto(), cargarGastoDTO.getMontoGasto());
            gastos.add(gastoNuevo);

            //Volver a guardar el reparto
            repartoRepository.save(reparto);

            return gastoNuevo;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    //SOLO DEBERIA RECIBIR EL IDREPARTO Y NO EL MONTO, YA QUE ESTE SE BUSCA EN LA BDD
    @Transactional
    @Override
    public Boolean finalizarReparto(FinalizarRepartoDTO finalizarRepartoDTO) throws Exception {
        try{
            if(repartoRepository.existsById(finalizarRepartoDTO.getIdReparto())){
                //Buscamos la instancia reparto correspondiente al id recibido
                Reparto repartoFinalizado = repartoRepository.findById(finalizarRepartoDTO.getIdReparto()).orElseThrow(()-> new RuntimeException("El reparto no existe"));

                //Le setea a la rendicion el monto total recaudado
                repartoFinalizado.getRendicion().setMontoRecaudado(finalizarRepartoDTO.getMontoRecaudado());
                //Se setean los gastos

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

    @Transactional
    @Override
    public Rendicion realizarRendicion(RealizarRendicionDTO realizarRendicionDTO) throws Exception {

        try{
            //Traer la rendicion del reparto ya creada
            Reparto r1 = repartoRepository.findById(realizarRendicionDTO.getIdReparto()).orElseThrow(() -> new RuntimeException("Reparto No encontrado"));
            Rendicion rendicion = r1.getRendicion();
            rendicion.setMontoRendido(realizarRendicionDTO.getMontoRendido());

            //Calculamos los gastos
            Double gastoTotal = 0D;

            for(Gasto gasto : rendicion.getGastos()){
                gastoTotal += gasto.getMontoGasto();
            }

            //Deben coincidir ambos montos de la rendicion MENOS los gastos
            if( rendicion.getMontoRendido().equals(rendicion.getMontoRecaudado() - gastoTotal)){
                rendicion.setDiferencia(0D);
                return rendicion;

            }else {
                //Calcula la diferencia entre montos
                rendicion.setDiferencia((rendicion.getMontoRecaudado() - gastoTotal) - rendicion.getMontoRendido());
                return  rendicion;
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
