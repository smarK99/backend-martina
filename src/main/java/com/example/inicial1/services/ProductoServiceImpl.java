package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaProductoDTO;
import com.example.inicial1.dtos.AltaProductoInsumoDTO;
import com.example.inicial1.entities.Producto;
import com.example.inicial1.entities.Producto;
import com.example.inicial1.entities.ProductoInsumo;
import com.example.inicial1.repositories.CategoriaRepository;
import com.example.inicial1.repositories.InsumoRepository;
import com.example.inicial1.repositories.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoServiceImpl extends BaseServiceImpl<Producto,Long> implements IProductoService{

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    InsumoRepository insumoRepository;

    @Transactional
    @Override
    public List<Producto> obtenerTodos() throws Exception {
        try {
            return productoRepository.obtenerTodos();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Producto crearProducto(AltaProductoDTO altaProductoDTO) throws Exception {

        try{
            Producto producto = Producto.builder()
                    .nombreProducto(altaProductoDTO.getNombreProducto())
                    .descripcionProducto(altaProductoDTO.getDescripcionProducto())
                    .fechaHoraAltaProducto(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .fechaHoraBajaProducto(null)
                    .categoria(categoriaRepository.findById(altaProductoDTO.getIdCategoria()).orElseThrow(() -> new RuntimeException("Categoria no encontrada")))
                    .productoInsumoList(new ArrayList<>())
                    .build();

            //Recorro todos los insumos con sus cantidades que se utilizan para crear el producto
            for(AltaProductoInsumoDTO api : altaProductoDTO.getApiList()){

                //Creo el producto insumo
                ProductoInsumo pi = ProductoInsumo.builder()
                        .cantidadInsumo(api.getCantidadI())
                        .insumo(insumoRepository.findById(api.getIdInsumo()).orElseThrow( () -> new RuntimeException("Insumo no encontrado")))
                        .build();
                //Lo agrego a la lista
                producto.getProductoInsumoList().add(pi);
            }

            return productoRepository.save(producto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Boolean borrarProducto(Long id) throws Exception {
        try{
            if(productoRepository.existsById(id)){
                Producto producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                producto.setFechaHoraBajaProducto(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                productoRepository.save(producto);
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
