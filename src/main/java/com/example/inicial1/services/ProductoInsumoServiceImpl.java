package com.example.inicial1.services;

import com.example.inicial1.dtos.ProductoInsumoDTO;
import com.example.inicial1.entities.Producto;
import com.example.inicial1.entities.ProductoInsumo;
import com.example.inicial1.repositories.InsumoRepository;
import com.example.inicial1.repositories.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoInsumoServiceImpl extends BaseServiceImpl<ProductoInsumo,Long> implements IProductoInsumoService{

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    InsumoRepository insumoRepository;

    @Transactional
    public ProductoInsumo configurarPI(ProductoInsumoDTO pidto) throws Exception {

        try {
            //Validar que existe el producto y el insumo que recibimos
            if(productoRepository.existsById(pidto.getIdProducto()) && insumoRepository.existsById(pidto.getIdInsumo())){

                //Se crea el nuevo producto insumo
                ProductoInsumo nuevoPi = ProductoInsumo.builder()
                        .cantidadInsumo(pidto.getCantidadPI())
                        .insumo(insumoRepository.findById(pidto.getIdInsumo()).orElseThrow(() -> new RuntimeException("Insumo no encontrado")))
                        .build();

                //Se vincula al producto que se trajo en el dto
                Producto producto = productoRepository.findById(pidto.getIdProducto()).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                List<ProductoInsumo> piList = producto.getProductoInsumoList();
                piList.add(nuevoPi);

                productoRepository.save(producto);

                return nuevoPi;
            }else{
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Problema en el servicio ProductoInsumo");
        }
    }

}
