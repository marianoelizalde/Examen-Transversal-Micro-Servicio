package com.Automotriz.vehiculoMS.service;

import com.Automotriz.vehiculoMS.model.Mantencion;
import com.Automotriz.vehiculoMS.repository.MantencionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class MantencionService {

    @Autowired
    private MantencionRepository mantencionRepository;

    public List<Mantencion> listar() {
        log.info("Listando todas las mantenciones");
        return mantencionRepository.findAll();
    }

    public Mantencion buscarPorId(Integer id) {
        log.info("Buscando mantención con ID: {}", id);
        return mantencionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Mantención con ID {} no encontrada", id);
                    return new RuntimeException("Mantencion no encontrada");
                });
    }

    public List<Mantencion> buscarPorVehiculo(Integer vehiculoId) {
        log.info("Buscando mantenciones del vehículo: {}", vehiculoId);
        return mantencionRepository.findByVehiculoId(vehiculoId);
    }

    public Mantencion guardar(Mantencion mantencion) {
        Mantencion resultado = mantencionRepository.save(mantencion);
        log.info("Mantención creada con ID: {} para vehículo: {}", resultado.getId(), resultado.getVehiculo().getId());
        return resultado;
    }

    public Mantencion actualizar(Integer id, Mantencion datos) {
        log.info("Actualizando mantención con ID: {}", id);
        Mantencion m = buscarPorId(id);
        m.setFechaIngreso(datos.getFechaIngreso());
        m.setCosto(datos.getCosto());
        Mantencion resultado = mantencionRepository.save(m);
        log.info("Mantención {} actualizada correctamente", id);
        return resultado;
    }

    public void eliminar(Integer id) {
        if (!mantencionRepository.existsById(id)) {
            log.error("No se puede eliminar. Mantención con ID {} no existe", id);
            throw new RuntimeException("Mantencion no existe");
        }
        mantencionRepository.deleteById(id);
        log.info("Mantención con ID {} eliminada correctamente", id);
    }
}
