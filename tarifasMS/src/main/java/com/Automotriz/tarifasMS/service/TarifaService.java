package com.Automotriz.tarifasMS.service;

import com.Automotriz.tarifasMS.model.Tarifa;
import com.Automotriz.tarifasMS.repository.TarifaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class TarifaService {

    @Autowired private TarifaRepository tarifaRepository;

    public List<Tarifa> listar() {
        log.info("Listando todas las tarifas");
        return tarifaRepository.findAll();
    }

    public Tarifa buscarPorId(Integer id) {
        log.info("Buscando tarifa con ID: {}", id);
        return tarifaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Tarifa con ID {} no encontrada", id);
                    return new RuntimeException("Tarifa no encontrada");
                });
    }

    public List<Tarifa> buscarPorVehiculo(Integer vehiculoId) {
        log.info("Buscando tarifas del vehículo: {}", vehiculoId);
        return tarifaRepository.findByVehiculoId(vehiculoId);
    }

    public List<Tarifa> buscarPorTemporada(String temporada) {
        log.info("Buscando tarifas con temporada: {}", temporada);
        return tarifaRepository.findByTemporada(temporada);
    }

    public List<Tarifa> listarActivas() {
        log.info("Listando tarifas activas");
        return tarifaRepository.findByEstado("ACTIVO");
    }

    public Tarifa guardar(Tarifa tarifa) {
        if (tarifa.getPrecioDia() == null || tarifa.getPrecioDia() <= 0) {
            log.error("Precio inválido al crear tarifa: {}", tarifa.getPrecioDia());
            throw new RuntimeException("El precio debe ser mayor a 0");
        }
        Tarifa resultado = tarifaRepository.save(tarifa);
        log.info("Tarifa creada con ID: {} para vehículo: {}", resultado.getId(), resultado.getVehiculoId());
        return resultado;
    }

    public Tarifa actualizar(Integer id, Tarifa datos) {
        log.info("Actualizando tarifa con ID: {}", id);
        Tarifa tarifa = buscarPorId(id);
        tarifa.setPrecioDia(datos.getPrecioDia());
        tarifa.setTemporada(datos.getTemporada());
        tarifa.setEstado(datos.getEstado());
        Tarifa resultado = tarifaRepository.save(tarifa);
        log.info("Tarifa {} actualizada correctamente", id);
        return resultado;
    }

    public void eliminar(Integer id) {
        if (!tarifaRepository.existsById(id)) {
            log.error("No se puede eliminar. Tarifa con ID {} no existe", id);
            throw new RuntimeException("Tarifa no existe");
        }
        tarifaRepository.deleteById(id);
        log.info("Tarifa con ID {} eliminada correctamente", id);
    }
}
