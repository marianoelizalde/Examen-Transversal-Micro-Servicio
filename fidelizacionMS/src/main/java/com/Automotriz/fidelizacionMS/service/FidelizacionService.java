package com.Automotriz.fidelizacionMS.service;

import com.Automotriz.fidelizacionMS.model.Fidelizacion;
import com.Automotriz.fidelizacionMS.repository.FidelizacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class FidelizacionService {

    @Autowired private FidelizacionRepository fidelizacionRepository;

    public List<Fidelizacion> listar() {
        log.info("Listando todos los perfiles de fidelización");
        return fidelizacionRepository.findAll();
    }

    public Fidelizacion buscarPorId(Integer id) {
        log.info("Buscando fidelización con ID: {}", id);
        return fidelizacionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Fidelización con ID {} no encontrada", id);
                    return new RuntimeException("Fidelizacion no encontrada");
                });
    }

    public Fidelizacion buscarPorRut(String rutCliente) {
        log.info("Buscando fidelización del cliente: {}", rutCliente);
        return fidelizacionRepository.findByRutCliente(rutCliente)
                .orElseThrow(() -> {
                    log.error("Fidelización del cliente {} no encontrada", rutCliente);
                    return new RuntimeException("Fidelizacion no encontrada");
                });
    }

    public List<Fidelizacion> buscarPorNivel(String nivelSocio) {
        log.info("Buscando clientes con nivel: {}", nivelSocio);
        return fidelizacionRepository.findByNivelSocio(nivelSocio);
    }

    public Fidelizacion guardar(Fidelizacion fidelizacion) {
        fidelizacion.setPuntosAcumulados(0);
        fidelizacion.setNivelSocio("BRONCE");
        Fidelizacion resultado = fidelizacionRepository.save(fidelizacion);
        log.info("Perfil de fidelización creado para cliente: {}", resultado.getRutCliente());
        return resultado;
    }

    public Fidelizacion agregarPuntos(String rutCliente, Integer puntos) {
        log.info("Agregando {} puntos al cliente: {}", puntos, rutCliente);
        try {
            Fidelizacion f = buscarPorRut(rutCliente);
            f.setPuntosAcumulados(f.getPuntosAcumulados() + puntos);
            int total = f.getPuntosAcumulados();
            if (total >= 10000)     f.setNivelSocio("PLATINO");
            else if (total >= 5000) f.setNivelSocio("ORO");
            else if (total >= 1000) f.setNivelSocio("PLATA");
            else                    f.setNivelSocio("BRONCE");
            Fidelizacion resultado = fidelizacionRepository.save(f);
            log.info("Puntos agregados correctamente al cliente: {}. Total: {} pts, Nivel: {}",
                    rutCliente, resultado.getPuntosAcumulados(), resultado.getNivelSocio());
            return resultado;
        } catch (RuntimeException e) {
            log.error("Error al agregar puntos al cliente: {}", rutCliente);
            throw e;
        }
    }

    public void eliminar(Integer id) {
        if (!fidelizacionRepository.existsById(id)) {
            log.error("No se puede eliminar. Fidelización con ID {} no existe", id);
            throw new RuntimeException("Fidelizacion no existe");
        }
        fidelizacionRepository.deleteById(id);
        log.info("Fidelización con ID {} eliminada correctamente", id);
    }
}
