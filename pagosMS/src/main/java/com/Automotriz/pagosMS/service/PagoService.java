package com.Automotriz.pagosMS.service;

import com.Automotriz.pagosMS.model.Pago;
import com.Automotriz.pagosMS.repository.PagoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class PagoService {

    @Autowired private PagoRepository pagoRepository;

    public List<Pago> listar() {
        log.info("Listando todos los pagos");
        return pagoRepository.findAll();
    }

    public Pago buscarPorId(Integer id) {
        log.info("Buscando pago con ID: {}", id);
        return pagoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Pago con ID {} no encontrado", id);
                    return new RuntimeException("Pago no encontrado");
                });
    }

    public List<Pago> buscarPorReserva(Integer reservaId) {
        log.info("Buscando pagos de la reserva: {}", reservaId);
        return pagoRepository.findByReservaId(reservaId);
    }

    public List<Pago> buscarPorEstado(String estadoPago) {
        log.info("Buscando pagos con estado: {}", estadoPago);
        return pagoRepository.findByEstadoPago(estadoPago);
    }

    public Pago guardar(Pago pago) {
        if (pago.getMonto() == null || pago.getMonto() <= 0) {
            log.error("Monto inválido al crear pago: {}", pago.getMonto());
            throw new RuntimeException("El monto debe ser mayor a 0");
        }
        pago.setEstadoPago("PENDIENTE");
        Pago resultado = pagoRepository.save(pago);
        log.info("Pago creado con ID: {} para reserva: {}", resultado.getId(), resultado.getReservaId());
        return resultado;
    }

    public Pago actualizar(Integer id, Pago datos) {
        log.info("Actualizando pago con ID: {}", id);
        Pago pago = buscarPorId(id);
        pago.setMonto(datos.getMonto());
        pago.setFechaPago(datos.getFechaPago());
        pago.setEstadoPago(datos.getEstadoPago());
        pago.setMetodoPago(datos.getMetodoPago());
        Pago resultado = pagoRepository.save(pago);
        log.info("Pago {} actualizado correctamente", id);
        return resultado;
    }

    public void eliminar(Integer id) {
        if (!pagoRepository.existsById(id)) {
            log.error("No se puede eliminar. Pago con ID {} no existe", id);
            throw new RuntimeException("Pago no existe");
        }
        pagoRepository.deleteById(id);
        log.info("Pago con ID {} eliminado correctamente", id);
    }
}
