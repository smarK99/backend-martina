package com.example.inicial1.services;

import com.example.inicial1.dtos.PreciosPorSucursalDTO;
import com.example.inicial1.dtos.SucursalProductoDTO;
import com.example.inicial1.entities.Producto;
import com.example.inicial1.entities.Sucursal;
import com.example.inicial1.entities.SucursalProducto;
import com.example.inicial1.repositories.ProductoRepository;
import com.example.inicial1.repositories.SucursalProductoRepository;
import com.example.inicial1.repositories.SucursalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SucursalProductoServiceImpl extends BaseServiceImpl<SucursalProducto,Long> implements ISucursalProductoService {

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    @Autowired
    SucursalProductoRepository sucursalProductoRepository;

    @Transactional
    public List<PreciosPorSucursalDTO> obtenerPreciosPorSucursal(Long idSucursal) throws Exception{
        try {
            if (sucursalRepository.existsById(idSucursal)) {
                //Pedimos el nombre de la Sucursal
                Optional<Sucursal> sucursal = sucursalRepository.findById(idSucursal);

                //Pedimos que nos traiga todos los precios de esa sucursal
                List<SucursalProducto> spList = sucursalProductoRepository.obtenerPreciosPorSucursal(idSucursal);

                //Creamos la lista de dtos
                List<PreciosPorSucursalDTO> preciosPorSucursalList = new ArrayList<>();

                for (SucursalProducto sp : spList){
                    //Armamos el DTO
                    PreciosPorSucursalDTO spdto = PreciosPorSucursalDTO.builder()
                            .nombreSucursal(sucursal.get().getNombreSucursal())
                            .nombreProducto(sp.getProducto().getNombreProducto())
                            .precioSP(sp.getPrecioSucursalProducto())
                            .build();

                    preciosPorSucursalList.add(spdto);
                }
                return preciosPorSucursalList;
            }else{
                return null;//Excepcion personalizada
            }

        } catch (Exception e) {
            e.printStackTrace(); // Esto imprime el stack trace en la consola
            throw new RuntimeException("Error al obtener los precios por sucursal");
        }
    }

    @Transactional
    public List<SucursalProducto> configurarPrecioSP(List<SucursalProductoDTO> spdtoList) throws Exception {
        try {
            // Asumimos que todos los DTOs vienen de la misma sucursal, así que buscamos la sucursal una sola vez usando el primer elemento
            if (spdtoList.isEmpty()) return new ArrayList<>();

            //Extraemos el id
            Long idSucursal = spdtoList.get(0).getIdSucursal();

            //Chequeamos si la sucursal con ese id existe
            Sucursal sucursal = sucursalRepository.findById(idSucursal)
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

            //Extraemos la lista de precios de esa sucursal
            List<SucursalProducto> spList = sucursal.getSucursalProductoList();

            //Recorremos la lista de dtos que representa cada precio de cada producto
            for (SucursalProductoDTO dto : spdtoList) {

                // Buscamos si la sucursal ya tiene un precio configurado para este producto
                Optional<SucursalProducto> spExistente = spList.stream()
                        .filter(sp -> sp.getProducto().getId().equals(dto.getIdProducto()))
                        .findFirst();

                // Si existe, le ACTUALIZAMOS el precio y la fecha
                if (spExistente.isPresent()) {
                    SucursalProducto sp = spExistente.get();
                    sp.setPrecioSucursalProducto(dto.getPrecioSP());
                    sp.setFechaHoraUltModif(LocalDateTime.now());
                } else {
                    // Si NO existe, creamos uno NUEVO y lo añadimos a la lista
                    Producto prod = productoRepository.findById(dto.getIdProducto())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                    SucursalProducto nuevosp = SucursalProducto.builder()
                            .precioSucursalProducto(dto.getPrecioSP())
                            .fechaHoraUltModif(LocalDateTime.now())
                            .producto(prod)
                            .build();
                    spList.add(nuevosp);
                }
            }

            sucursalRepository.save(sucursal);
            return spList;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }



//        try {
//            //VALIDAMOS LA EXISTENCIA DEL ID DE SUCURSAL Y PRODUCTO
//            if(productoRepository.existsById(spdto.getIdProducto()) && sucursalRepository.existsById(spdto.getIdSucursal())) {
//
//                //Creamos la nueva instancia de SucursalProducto
//                SucursalProducto nuevosp = SucursalProducto.builder()
//                        .precioSucursalProducto(spdto.getPrecioSP())
//                        .fechaHoraUltModif(LocalDateTime.now())
//                        .producto(productoRepository.findById(spdto.getIdProducto()).orElseThrow(() -> new RuntimeException("Producto no encontrado")))
//                        .build();
//
//                //Traemos de bdd la sucursal correspondiente
//                //Al poner una excepcion aqui nos ahorramos de usar el optional. En caso de usar esto, usar optional.get().
//                Sucursal sucursal = sucursalRepository.findById(spdto.getIdSucursal())
//                        .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
//
//                //Accedemos a la lista de precios
//                List<SucursalProducto> spList = sucursal.getSucursalProductoList();
//
//                //Le asignamos el nuevo precio
//                spList.add(nuevosp);
//
//                //Guardamos la sucursal con el nuevo precio, se actualiza la lista de SucursalProducto
//                sucursalRepository.save(sucursal);
//
//                //Retornamos solo el sp sin guardarlo en el repo por que se guarda con el cascade de la sucursal
//                return nuevosp;
//            }else {
//                //Aqui deberia crear una excepcion personalizada que tenga el mensaje de "Fallo la validacion de producto o sucursal"
//                return null;
//            }
//
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }


}


