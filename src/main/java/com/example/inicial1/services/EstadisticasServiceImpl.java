package com.example.inicial1.services;

import com.example.inicial1.dtos.ProductosMasVendidos;
import com.example.inicial1.dtos.ResumenVentasDTO;
import com.example.inicial1.dtos.TotalVtasPorSucursalDTO;
import com.example.inicial1.repositories.DetallePedidoRepository;
import com.example.inicial1.repositories.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EstadisticasServiceImpl {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    DetallePedidoRepository detallePedidoRepository;

    //Obtener monto total de ventas por Sucursal
    @Transactional
    public List<TotalVtasPorSucursalDTO> obtenerTotalVtasPorSucursal() throws Exception {
        try {
            return pedidoRepository.obtenerTotalVentasPorSucursal();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    //Obtener producto mas vendido
    @Transactional
    public List<ProductosMasVendidos> obtenerProdsMasVendidos() throws Exception {
        try {
            LocalDate fechaLimite = LocalDate.now().minusDays(30);
            return detallePedidoRepository.obtenerProdsMasVendidos(fechaLimite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    //Obtener monto recaudado ultimos 30 dias
    @Transactional
    public ResumenVentasDTO obtenerRecaudacionUltimos30Dias() throws Exception {
        try {
            //Calculamos la fecha antes para no tener problemas con los cambios de bdd
            LocalDate limite30Dias = LocalDate.now().minusDays(30);
            return pedidoRepository.obtenerRecaudacionUltimos30Dias(limite30Dias);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    //Obtener monto recaudado ultimos 7 dias
    @Transactional
    public Double obtenerRecaudacionUltimos7Dias() throws Exception {
        try {
            LocalDate limite7Dias = LocalDate.now().minusDays(7);
            return pedidoRepository.obtenerRecaudacionUltimos7Dias(limite7Dias);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
