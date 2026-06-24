package com.Automotriz.usuarioMS.service;

import com.Automotriz.usuarioMS.model.Antecedentes;
import com.Automotriz.usuarioMS.repository.AntecedentesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class AntecedentesService {

    @Autowired
    private AntecedentesRepository antecedentesRepository;

    public List<Antecedentes> listar() {
        log.info("Listando todos los antecedentes");
        return antecedentesRepository.findAll();
    }

    public Antecedentes buscarPorId(Integer id) {
        log.info("Buscando antecedentes con ID: {}", id);
        return antecedentesRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Antecedentes con ID {} no encontrados", id);
                    return new RuntimeException("Antecedentes no encontrados");
                });
    }

    public Antecedentes buscarPorCliente(Integer clienteId) {
        log.info("Buscando antecedentes del cliente: {}", clienteId);
        return antecedentesRepository.findByClienteId(clienteId)
                .orElseThrow(() -> {
                    log.error("Antecedentes del cliente {} no encontrados", clienteId);
                    return new RuntimeException("Antecedentes no encontrados");
                });
    }

    public Antecedentes guardar(Antecedentes antecedentes) {
        Antecedentes resultado = antecedentesRepository.save(antecedentes);
        log.info("Antecedentes creados con ID: {} para cliente: {}", resultado.getId(), resultado.getCliente().getId());
        return resultado;
    }

    public Antecedentes actualizar(Integer id, Antecedentes datos) {
        log.info("Actualizando antecedentes con ID: {}", id);
        Antecedentes ant = buscarPorId(id);
        ant.setEstadoLicencia(datos.getEstadoLicencia());
        ant.setRegistroMultas(datos.getRegistroMultas());
        Antecedentes resultado = antecedentesRepository.save(ant);
        log.info("Antecedentes {} actualizados correctamente", id);
        return resultado;
    }

    public void eliminar(Integer id) {
        if (!antecedentesRepository.existsById(id)) {
            log.error("No se pueden eliminar. Antecedentes con ID {} no existen", id);
            throw new RuntimeException("Antecedentes no existen");
        }
        antecedentesRepository.deleteById(id);
        log.info("Antecedentes con ID {} eliminados correctamente", id);
    }
}
