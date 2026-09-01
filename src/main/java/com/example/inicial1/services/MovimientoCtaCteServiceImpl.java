package com.example.inicial1.services;

import com.example.inicial1.entities.MovimientoCtaCte;
import com.example.inicial1.entities.Sucursal;
import com.example.inicial1.repositories.MovimientoCtaCteRepository;
import com.example.inicial1.repositories.SucursalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimientoCtaCteServiceImpl extends BaseServiceImpl<MovimientoCtaCte,Long> implements IMovimientoCtaCteService{

    @Autowired
    private MovimientoCtaCteRepository movimientoRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Override
    @Transactional
    public List<MovimientoCtaCte> obtenerHistorial(Long idSucursal) throws Exception {
        try {
            return movimientoRepository.obtenerHistorialPorSucursal(idSucursal);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public MovimientoCtaCte registrarMovimiento(MovimientoCtaCte nuevoMovimiento) throws Exception {
        try {

            // 1. Extraemos el ID de la sucursal que viene en el JSON del frontend
            if (nuevoMovimiento.getSucursal() == null || nuevoMovimiento.getSucursal().getId() == null) {
                throw new RuntimeException("El movimiento debe estar asociado a una sucursal.");
            }
            Long idSucursal = nuevoMovimiento.getSucursal().getId();

            // 2. Validar y setear Sucursal y Fecha
            Sucursal sucursal = sucursalRepository.findById(idSucursal)
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

            nuevoMovimiento.setSucursal(sucursal);
            nuevoMovimiento.setFechaHora(LocalDateTime.now());

            // 3. Obtener el saldo de la fila anterior (Limitado a 1 resultado)
            List<MovimientoCtaCte> ultimos = movimientoRepository.obtenerUltimoMovimiento(idSucursal, PageRequest.of(0, 1));
            Double deudaAnterior = 0D;

            if (!ultimos.isEmpty()) {
                deudaAnterior = ultimos.get(0).getSaldoRestante();
            }

            // 4. Control de valores nulos (por seguridad)
            Double importe = nuevoMovimiento.getImportePedido() != null ? nuevoMovimiento.getImportePedido() : 0D;
            Double pago = nuevoMovimiento.getMontoPagado() != null ? nuevoMovimiento.getMontoPagado() : 0D;

            // 5. Nueva Deuda = (Deuda Anterior) + (Lo que cuesta el pedido nuevo) - (La plata que nos dio en mano)
            Double nuevaDeuda = deudaAnterior + importe - pago;

            nuevoMovimiento.setSaldoRestante(nuevaDeuda);

            // 6. Guardar y retornar
            return movimientoRepository.save(nuevoMovimiento);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
