package com.example.inicial1.services;

import com.example.inicial1.dtos.DetallePedidoDTO;
import com.example.inicial1.dtos.DetallePedidoDTO2;
import com.example.inicial1.dtos.PedidoDTO;
import com.example.inicial1.dtos.PedidosPorSucursalDTO;
import com.example.inicial1.entities.*;
import com.example.inicial1.repositories.EstadoPedidoRepository;
import com.example.inicial1.repositories.PedidoRepository;
import com.example.inicial1.repositories.ProductoRepository;
import com.example.inicial1.repositories.SucursalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.channels.ScatteringByteChannel;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PedidoServiceImpl extends BaseServiceImpl<Pedido,Long> implements IPedidoService{

    @Autowired
    SucursalRepository sucursalRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    EstadoPedidoRepository estadoPedidoRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Transactional
    public Pedido generarPedido(PedidoDTO pedidoDTO) throws Exception{
        try{

            System.out.println("Cantidad de detalles en DTO: " + pedidoDTO.getDpdtoList().size());

            Sucursal sucursal = sucursalRepository.findById(pedidoDTO.getIdSucursal()).orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

            Pedido nuevoPedido = Pedido.builder()
                    .descripcionPedido(pedidoDTO.getDescripcionPedido())
                    .estadoPedido(estadoPedidoRepository.findById(1L).orElseThrow(() -> new RuntimeException("EstadoPedido no encontrado")))
                    .fechaHoraAltaPedido(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .fechaHoraBajaPedido(null)
                    .sucursal(sucursal)
                    .detallePedidoList(new ArrayList<>())
                    .build();

            double totalDetalles = 0.0;

            //Buscar precio de cada item y setearselo al pedido
            //En caso de que la entidad sucursal-producto no exista
            // (es decir que no se haya configurado precio de un producto para una sucursal),
            // este for solo persistira los detalles que tengan su correspondiente SucursalProducto
            for(DetallePedidoDTO dpdto : pedidoDTO.getDpdtoList()){
                for(SucursalProducto sp : sucursal.getSucursalProductoList()) {
                    if (dpdto.getIdProducto().equals(sp.getProducto().getId())) { //Aqui el sp.getProducto.getId nunca se validara si no se creo la SucProd correspondiente

                        System.out.println("Agregando detalle para producto ID: " + dpdto.getIdProducto());

                        DetallePedido nuevoDP = DetallePedido.builder()
                                .cantidadDetallePedido(dpdto.getCantidadDetallePedido())
                                .producto(productoRepository.findById(dpdto.getIdProducto()).orElseThrow(() -> new RuntimeException("Producto no encontrado")))
                                .build();

                        //Obtengo el subtotal del detalle actual
                        double subtotalDetalle = dpdto.getCantidadDetallePedido() * sp.getPrecioSucursalProducto();

                        //Lo sumo a los otros detalles
                        totalDetalles += subtotalDetalle;

                        //Seteo el subtotal al objeto nuevo de DetallePedido
                        nuevoDP.setSubtotalDetallePedido(subtotalDetalle);

                        //Le agrego el DetallePedido actual al Pedido nuevo
                        nuevoPedido.getDetallePedidoList().add(nuevoDP);

                        //Rompo la ejecucion del for de sucursales
                        break;
                    }
                }
            }
            //Seteo el total de los detalles a la variable correspondiente dentro de Pedido
            nuevoPedido.setImporteTotalPedido(totalDetalles);

            System.out.println("Cantidad de detalles a persistir: " + nuevoPedido.getDetallePedidoList().size());

            //Retorno el pedido nuevo
            return pedidoRepository.save(nuevoPedido);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public List<PedidosPorSucursalDTO> buscarPedidosPorSucursal(Long idSucursal) throws Exception {
        try{
            if (sucursalRepository.existsById(idSucursal)){
                //Lista que voy devolver con los DTO'S
                List<PedidosPorSucursalDTO> ppsdtoList = new ArrayList<>();

                //Lista devuelta por la bdd
                List<Pedido> pedidosList = pedidoRepository.buscarPedidosPorSucursal(idSucursal);

                //Recorro la lista obtenida desde la base de datos
                for(Pedido p : pedidosList){

                    //Obtengo la lista de DP de cada pedido
                    List<DetallePedido> detallePedidoList = p.getDetallePedidoList();
                    //Creo una lista de DTO que voy a mostrar con los datos requeridos
                    List<DetallePedidoDTO2> detallePedidoDTO2List = new ArrayList<>();

                    //Itero los detallePedido
                    for(DetallePedido dp : detallePedidoList){

                        //Creo un DTO para mostrar datos seleccionados de DP y producto
                        DetallePedidoDTO2 dpdto2 = DetallePedidoDTO2.builder()
                                .idProducto(dp.getProducto().getId())
                                .nombreProducto(dp.getProducto().getNombreProducto())
                                .cantidadDP(dp.getCantidadDetallePedido())
                                .subtotalDP(dp.getSubtotalDetallePedido())
                                .build();

                        detallePedidoDTO2List.add(dpdto2);
                    }

                    //Armo mi dto con los datos que voy a mostrar
                    PedidosPorSucursalDTO ppsdto = PedidosPorSucursalDTO.builder()
                            .idPedido(p.getId())
                            .descripcionPedido(p.getDescripcionPedido())
                            .fechaHoraAltaPedido(p.getFechaHoraAltaPedido())
                            .fechaHoraBajaPedido(p.getFechaHoraBajaPedido())
                            .totalImportePedido(p.getImporteTotalPedido())
                            .nombreEstadoPedido(p.getEstadoPedido().getNombreEstadoPedido())
                            .detallePedidoDTO2List(detallePedidoDTO2List)
                            .build();

                    ppsdtoList.add(ppsdto);
                }

                return ppsdtoList;
            }
            else{
                return Collections.emptyList();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


}
