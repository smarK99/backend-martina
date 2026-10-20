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

                    // Aseguramos que no sean null
                    Double efvo = entregarPedidoDTO.getMontoEfectivo() != null ? entregarPedidoDTO.getMontoEfectivo() : 0D;
                    // Recibimos la transferencia, pero ya NO la sumamos a un "totalCobrado"
                    Double transf = entregarPedidoDTO.getMontoTransferencia() != null ? entregarPedidoDTO.getMontoTransferencia() : 0D;

                    // 2. Logística de Caja: SOLO EL EFECTIVO va a la Rendición del Repartidor
                    if (efvo > 0) {
                        Double recaudadoActual = r.getRendicion().getMontoRecaudado() == null ? 0D : r.getRendicion().getMontoRecaudado();
                        r.getRendicion().setMontoRecaudado(recaudadoActual + efvo);
                    }

                    // 3. Logística de Cuenta Corriente: SOLO EL EFECTIVO achica la deuda hoy
                    if (efvo > 0) {
                        MovimientoCtaCte mov = new MovimientoCtaCte();
                        mov.setSucursal(p.getSucursal());
                        mov.setConcepto("Cobro en Efectivo - Reparto Pedido #" + p.getId());
                        mov.setImportePedido(0D);
                        mov.setMontoPagado(efvo); // Solo impacta el billete físico

                        movimientoCtaCteService.registrarMovimiento(mov);
                    }

                    // Si la transferencia > 0, NO HACEMOS NADA.
                    // La deuda sigue viva en la Cta Cte para que el Dueño la salde después.

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
            // 1. Traer la rendicion del reparto ya creada
            Reparto r1 = repartoRepository.findById(realizarRendicionDTO.getIdReparto())
                    .orElseThrow(() -> new RuntimeException("Reparto No encontrado"));

            // 2. Verificar si el estado del reparto esta finalizado
            if(!r1.getEstadoReparto().getNombreEstadoReparto().equals("FINALIZADO")){
                throw new RuntimeException("El reparto no esta finalizado todavía");
            }

            Rendicion rendicion = r1.getRendicion();

            // 3. Calcular el total del reparto
            Double montoTotal = 0D;
            if (r1.getPedidosList() != null) {
                for(Pedido p : r1.getPedidosList()){
                    Double importePedido = p.getImporteTotalPedido() != null ? p.getImporteTotalPedido() : 0D;
                    montoTotal += importePedido;
                }
            }
            rendicion.setMontoTotalReparto(montoTotal);

            // 4. Seteamos el monto rendido asegurando que no sea nulo
            Double montoRendido = realizarRendicionDTO.getMontoRendido() != null ? realizarRendicionDTO.getMontoRendido() : 0D;
            rendicion.setMontoRendido(montoRendido);

            // 5. Calculamos los gastos asegurando la nulidad
            Double gastoTotal = 0D;
            if (rendicion.getGastos() != null) {
                for(Gasto gasto : rendicion.getGastos()){
                    Double montoGasto = gasto.getMontoGasto() != null ? gasto.getMontoGasto() : 0D;
                    gastoTotal += montoGasto;
                }
            }

            // 6. Previene error de null pointer exception cuando el monto recaudado en el reparto ha sido cero
            Double recaudadoActual = rendicion.getMontoRecaudado() != null ? rendicion.getMontoRecaudado() : 0D;

            // 7. Aplicamos las restas con los valores seguros
            rendicion.setMontoRecaudado(recaudadoActual - gastoTotal);
            rendicion.setDiferencia(montoRendido - rendicion.getMontoRecaudado());

            return rendicion;

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
