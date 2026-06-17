package com.Automotriz.documentosMS.service;

import com.Automotriz.documentosMS.model.Contrato;
import com.Automotriz.documentosMS.repository.ContratoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class ContratoService {

    @Autowired private ContratoRepository contratoRepository;

    public List<Contrato> listar() {
        log.info("Listando todos los contratos");
        return contratoRepository.findAll();
    }

    public Contrato buscarPorId(Integer id) {
        log.info("Buscando contrato con ID: {}", id);
        return contratoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Contrato con ID {} no encontrado", id);
                    return new RuntimeException("Contrato no encontrado");
                });
    }

    public List<Contrato> buscarPorReserva(Integer reservaId) {
        log.info("Buscando contratos de la reserva: {}", reservaId);
        return contratoRepository.findByReservaId(reservaId);
    }

    public Contrato guardar(Contrato contrato) {
        contrato.setEstado("PENDIENTE");
        Contrato resultado = contratoRepository.save(contrato);
        log.info("Contrato creado con ID: {} para reserva: {}", resultado.getId(), resultado.getReservaId());
        return resultado;
    }

    public Contrato actualizar(Integer id, Contrato datos) {
        log.info("Actualizando contrato con ID: {}", id);
        Contrato contrato = buscarPorId(id);
        contrato.setEstado(datos.getEstado());
        contrato.setClausulas(datos.getClausulas());
        Contrato resultado = contratoRepository.save(contrato);
        log.info("Contrato {} actualizado correctamente", id);
        return resultado;
    }

    public void eliminar(Integer id) {
        if (!contratoRepository.existsById(id)) {
            log.error("No se puede eliminar. Contrato con ID {} no existe", id);
            throw new RuntimeException("Contrato no existe");
        }
        contratoRepository.deleteById(id);
        log.info("Contrato con ID {} eliminado correctamente", id);
    }
}
