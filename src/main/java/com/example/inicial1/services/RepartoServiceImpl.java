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

    @Autowired
    MovimientoCtaCteServiceImpl movimientoCtaCteService;

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
                        .estadoReparto(estadoRepartoRepository.findById(1L).orElseThrow(() -> new RuntimeException("EstadoReparto 'EN_CURSO' no encontrado")))
                        .usuario(usuarioRepository.findById(repartoDTO.getIdUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado")))
                        .pedidosList(new ArrayList<>())
                        .rendicion(new Rendicion(null, null, null, null, null))
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
        try{
            //Primero busco si existe la instancia
            Reparto reparto = repartoRepository.findById(idReparto).orElseThrow(() -> new RuntimeException("El reparto no existe"));
            //Valido si el estado del reparto es distinto a finalizado
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

    //MARCAR PEDIDO DEL REPARTO COMO ENTREGADO Y SU METODO DE PAGO
    @Transactional
    @Override
    public Boolean entregarPedido(EntregarPedidoDTO entregarPedidoDTO) throws Exception {
        try{
            Reparto r = repartoRepository.findById(entregarPedidoDTO.getIdReparto())
                    .orElseThrow(() -> new RuntimeException("Reparto no encontrado"));

            for (Pedido p : r.getPedidosList()) {
                if (p.getId().equals(entregarPedidoDTO.getIdPedido())) {

                    // 1. Marcamos como entregado
                    p.setEstadoPedido(estadoPedidoRepository.findById(4L).get());

                    // Aseguramos que no sean null (valores por defecto 0)
                    Double efvo = entregarPedidoDTO.getMontoEfectivo() != null ? entregarPedidoDTO.getMontoEfectivo() : 0D;
                    Double transf = entregarPedidoDTO.getMontoTransferencia() != null ? entregarPedidoDTO.getMontoTransferencia() : 0D;
                    Double totalCobrado = efvo + transf;

                    // 2. Logística de Caja: SOLO EL EFECTIVO va a la Rendición del Repartidor
                    if (efvo > 0) {
                        Double recaudadoActual = r.getRendicion().getMontoRecaudado() == null ? 0D : r.getRendicion().getMontoRecaudado();
                        r.getRendicion().setMontoRecaudado(recaudadoActual + efvo);
                    }

                    // 3. Logística de Cuenta Corriente: Ambos montos pagan deuda
                    if (totalCobrado > 0) {
                        MovimientoCtaCte mov = new MovimientoCtaCte();
                        mov.setSucursal(p.getSucursal());
                        //mov.setPedido(p);
                        mov.setConcepto("Cobro en calle Pedido #" + p.getId());
                        mov.setImportePedido(0D);
                        mov.setMontoPagado(totalCobrado); // Achica la deuda

                        movimientoCtaCteService.registrarMovimiento(mov);
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

            //Valido que el reparto no haya finalizado
            if (reparto.getEstadoReparto() != null && reparto.getEstadoReparto().getNombreEstadoReparto().equalsIgnoreCase("Finalizado")) {
                throw new RuntimeException("No se pueden cargar gastos a un reparto que ya está finalizado.");
            }

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


    @Transactional
    @Override
    public Reparto finalizarReparto(Long idReparto) throws Exception {
        try{
                //Buscamos la instancia reparto correspondiente al id recibido
                Reparto repartoFinalizado = repartoRepository.findById(idReparto).orElseThrow(()-> new RuntimeException("El reparto no existe"));

                //Chequeamos si todos los pedidos estan entregados
                for(Pedido p: repartoFinalizado.getPedidosList()){
                    if(!p.getEstadoPedido().getNombreEstadoPedido().equals("ENTREGADO")){
                        throw new RuntimeException("Hay un pedido no entregado");
                    }
                }

                //Le seteamos el estado reparto finalizado (id 2)
                repartoFinalizado.setEstadoReparto(estadoRepartoRepository.findById(2L).orElseThrow(()-> new RuntimeException("El EstadoReparto no existe")));
                repartoFinalizado.setFechaHoraFinReparto(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

                return repartoRepository.save(repartoFinalizado);
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

            //Tengo que verificar si el estado del reparto esta finalizado si no no se puede hacer la rendicion todavia
            if(!r1.getEstadoReparto().getNombreEstadoReparto().equals("FINALIZADO")){
                throw new RuntimeException("El reparto no esta finalizado todavía");
            }

            //Obtengo la rendicion
            Rendicion rendicion = r1.getRendicion();

            //Variable para guardar el monto total del reparto
            Double montoTotal = 0D;

            //Calcular el total del reparto (suma de valores de todos los pedidos sin importar si se pagaron o no sin gastos incluidos)
            for(Pedido p : r1.getPedidosList()){
                montoTotal += p.getImporteTotalPedido();
            }

            //Seteamos el monto total del reparto calculado en el for anterior
            rendicion.setMontoTotalReparto(montoTotal);

            //Le setea a la rendicion el monto total recaudado (contado por el repartidor al final de su recorrido)
            r1.getRendicion().setMontoRendido(realizarRendicionDTO.getMontoRendido());

            //Calculamos los gastos
            Double gastoTotal = 0D;

            for(Gasto gasto : rendicion.getGastos()){
                gastoTotal += gasto.getMontoGasto();
            }

            //Debemos restarle al monto recaudado los gastos realizados
            rendicion.setMontoRecaudado(rendicion.getMontoRecaudado() - gastoTotal);

            //Calculo de rendicion y diferencia
            rendicion.setDiferencia(r1.getRendicion().getMontoRendido() - rendicion.getMontoRecaudado());

            return rendicion;
//            //Deben coincidir ambos montos de la rendicion MENOS los gastos
//            if( rendicion.getMontoRendido().equals(rendicion.getMontoRecaudado() - gastoTotal)){
//                rendicion.setDiferencia(0D);
//                return rendicion;
//
//            }else {
//                //Calcula la diferencia entre montos
//                rendicion.setDiferencia((rendicion.getMontoRecaudado() - gastoTotal) - rendicion.getMontoRendido());
//                return rendicion;
//            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
