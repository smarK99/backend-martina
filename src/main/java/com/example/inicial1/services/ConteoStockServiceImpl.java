package com.example.inicial1.services;

import com.example.inicial1.dtos.*;
import com.example.inicial1.entities.*;
import com.example.inicial1.entities.ConteoStock;
import com.example.inicial1.repositories.ConteoStockRepository;
import com.example.inicial1.repositories.InsumoRepository;
import com.example.inicial1.repositories.ProductoRepository;
import com.example.inicial1.repositories.UsuarioRepository;
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
            for(AltaConteoStockInsumoDTO acsidto : altaConteoStockDTO.getInsumoDTOList()){
                //Creo la clase correspondiente
                ConteoStockInsumo csi = ConteoStockInsumo.builder()
                        .cantidadStockInsumo(acsidto.getCantidadStockInsumo())
                        .insumo(insumoRepository.findById(acsidto.getIdInsumo()).orElseThrow(() -> new RuntimeException("Insumo no encontrado")))
                        .build();
                //Añado la clase a la lista de conteos en la clase ConteoStock
                conteoStock.getCsinsumosList().add(csi);
            }

            //Recorro la lista de productos contados
            for(AltaConteoStockProductoDTO acspdto : altaConteoStockDTO.getProductoDTOList()){
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

    @Transactional
    @Override
    public List<ConteoStock> obtenerTodos() throws Exception {
        try {
            return conteoStockRepository.obtenerTodos();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    @Override
    public ConteoStock update(Long id, UpdateControlStockDTO csdto) throws Exception {
        try {
            //Impresion de prueba
            System.out.println("CANTIDAD INSUMOS RECIBIDOS: " + (csdto.getInsumoDTOList() != null ? csdto.getInsumoDTOList().size() : "NULL"));
            System.out.println("CANTIDAD PRODUCTOS RECIBIDOS: " + (csdto.getProductoDTOList() != null ? csdto.getProductoDTOList().size() : "NULL"));

            // 1. Buscamos el conteo viejo en la base de datos
            ConteoStock conteoViejo = conteoStockRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Conteo no encontrado"));

            // 2. Actualizamos datos básicos
            Usuario usuario = usuarioRepository.findById(csdto.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            conteoViejo.setUsuario(usuario);
            conteoViejo.setFechaHoraAltaConteoStock(LocalDateTime.now()); //Actualizamos su fecha de alta

            // 3. VACIAR LAS LISTAS VIEJAS

            conteoViejo.getCsinsumosList().clear();
            conteoViejo.getCsproductosList().clear();

            // 4. LLENAR CON LOS DATOS NUEVOS

            // -- Para Insumos --
            if (csdto.getInsumoDTOList() != null) {
                for (ItemInsumoDTO item : csdto.getInsumoDTOList()) {
                    Insumo insumoDB = insumoRepository.findById(item.getIdInsumo()).orElseThrow();

                    ConteoStockInsumo nuevoCsi = new ConteoStockInsumo();
                    nuevoCsi.setInsumo(insumoDB);
                    nuevoCsi.setCantidadStockInsumo(item.getCantidadStockInsumo());

                    conteoViejo.getCsinsumosList().add(nuevoCsi);
                }
            }

            // -- Para Productos --
            if (csdto.getProductoDTOList() != null) {
                for (ItemProductoDTO item : csdto.getProductoDTOList()) {
                    Producto prodDB = productoRepository.findById(item.getIdProducto()).orElseThrow();

                    ConteoStockProducto nuevoCsp = new ConteoStockProducto();
                    nuevoCsp.setProducto(prodDB);
                    nuevoCsp.setCantidadStockProducto(item.getCantidadStockProducto());

                    conteoViejo.getCsproductosList().add(nuevoCsp);
                }
            }

            // 5. Guardamos y retornamos
            return conteoStockRepository.save(conteoViejo);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    @Transactional
    @Override
    public Boolean delete(Long id) throws Exception{
        try{
            if(conteoStockRepository.existsById(id)){
                ConteoStock conteo = conteoStockRepository.findById(id).orElseThrow(() -> new RuntimeException("Conteo no encontrado"));
                conteo.setFechaHoraBajaConteoStock(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                conteoStockRepository.save(conteo);
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

    // ==========================================
    // NUEVO MÉTODO PARA PAGINACIÓN Y BÚSQUEDA
    // ==========================================
    public Page<ConteoStock> buscarPaginadoYFiltrado(String termino, String fecha, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return conteoStockRepository.buscarPaginadoYFiltrado(termino, fecha, pageable);
    }
}