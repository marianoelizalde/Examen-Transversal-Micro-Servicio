package com.Automotriz.vehiculoMS.service;

import com.Automotriz.vehiculoMS.model.Vehiculo;
import com.Automotriz.vehiculoMS.repository.VehiculoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class VehiculoService {

    @Autowired private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> listar() {
        log.info("Listando todos los vehículos");
        return vehiculoRepository.findAll();
    }

    public List<Vehiculo> listarDisponibles() {
        log.info("Listando vehículos disponibles");
        return vehiculoRepository.findByEstado("DISPONIBLE");
    }

    public Vehiculo buscarPorId(Integer id) {
        log.info("Buscando vehículo con ID: {}", id);
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Vehículo con ID {} no encontrado", id);
                    return new RuntimeException("Vehiculo no encontrado");
                });
    }

    public Vehiculo buscarPorPatente(String patente) {
        log.info("Buscando vehículo con patente: {}", patente);
        return vehiculoRepository.findByPatente(patente)
                .orElseThrow(() -> {
                    log.error("Vehículo con patente {} no encontrado", patente);
                    return new RuntimeException("Vehiculo no encontrado");
                });
    }

    public Vehiculo guardar(Vehiculo vehiculo) {
        Vehiculo resultado = vehiculoRepository.save(vehiculo);
        log.info("Vehículo creado con ID: {} patente: {}", resultado.getId(), resultado.getPatente());
        return resultado;
    }

    public Vehiculo actualizar(Integer id, Vehiculo datos) {
        log.info("Actualizando vehículo con ID: {}", id);
        Vehiculo vehiculo = buscarPorId(id);
        vehiculo.setNombre(datos.getNombre());
        vehiculo.setModelo(datos.getModelo());
        vehiculo.setMarca(datos.getMarca());
        vehiculo.setAnio(datos.getAnio());
        vehiculo.setEstado(datos.getEstado());
        vehiculo.setPatente(datos.getPatente());
        Vehiculo resultado = vehiculoRepository.save(vehiculo);
        log.info("Vehículo {} actualizado correctamente", id);
        return resultado;
    }

    public void eliminar(Integer id) {
        if (!vehiculoRepository.existsById(id)) {
            log.error("No se puede eliminar. Vehículo con ID {} no existe", id);
            throw new RuntimeException("Vehiculo no existe");
        }
        vehiculoRepository.deleteById(id);
        log.info("Vehículo con ID {} eliminado correctamente", id);
    }
}
