package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaConteoStockDTO;
import com.example.inicial1.dtos.AltaConteoStockInsumoDTO;
import com.example.inicial1.dtos.AltaConteoStockProductoDTO;
import com.example.inicial1.entities.*;
import com.example.inicial1.entities.ConteoStock;
import com.example.inicial1.repositories.ConteoStockRepository;
import com.example.inicial1.repositories.InsumoRepository;
import com.example.inicial1.repositories.ProductoRepository;
import com.example.inicial1.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConteoStockServiceImpl extends BaseServiceImpl<ConteoStock,Long> implements IConteoStockService{

    @Autowired
    ConteoStockRepository conteoStockRepository;

    @Autowired
    InsumoRepository insumoRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Transactional
    @Override
    public ConteoStock crearConteoStock(AltaConteoStockDTO altaConteoStockDTO) throws Exception {

        try{
            //Instancio la clase conteo
            ConteoStock conteoStock = ConteoStock.builder()
                    .fechaHoraAltaConteoStock(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .csinsumosList(new ArrayList<>())
                    .csproductosList(new ArrayList<>())
                    .usuario(usuarioRepository.findById(altaConteoStockDTO.getIdUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado")))
                    .build();

            //Recorro la lista de insumos contados
            for(AltaConteoStockInsumoDTO acsidto : altaConteoStockDTO.getInsumosDTOList()){
                //Creo la clase correspondiente
                ConteoStockInsumo csi = ConteoStockInsumo.builder()
                        .cantidadStockInsumo(acsidto.getCantidadStockInsumo())
                        .insumo(insumoRepository.findById(acsidto.getIdInsumo()).orElseThrow(() -> new RuntimeException("Insumo no encontrado")))
                        .build();
                //Añado la clase a la lista de conteos en la clase ConteoStock
                conteoStock.getCsinsumosList().add(csi);
            }

            //Recorro la lista de productos contados
            for(AltaConteoStockProductoDTO acspdto : altaConteoStockDTO.getProductosDTOList()){
                //Creo la clase correspondiente
                ConteoStockProducto csp = ConteoStockProducto.builder()
                        .cantidadStockProducto(acspdto.getCantidadStockProducto())
                        .producto(productoRepository.findById(acspdto.getIdProducto()).orElseThrow(() -> new RuntimeException("Producto no encontrado")))
                        .build();
                //Añado la clase a la lista de conteos en la clase ConteoStock
                conteoStock.getCsproductosList().add(csp);
            }


            return conteoStockRepository.save(conteoStock);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
