package com.Automotriz.sucursalMS.service;

import com.Automotriz.sucursalMS.model.Sucursal;
import com.Automotriz.sucursalMS.repository.SucursalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class SucursalService {

    @Autowired private SucursalRepository sucursalRepository;

    public List<Sucursal> listar() {
        log.info("Listando todas las sucursales");
        return sucursalRepository.findAll();
    }

    public List<Sucursal> buscarPorComuna(String comuna) {
        log.info("Buscando sucursales en la comuna: {}", comuna);
        return sucursalRepository.findByComuna(comuna);
    }

    public Sucursal buscarPorId(Integer id) {
        log.info("Buscando sucursal con ID: {}", id);
        return sucursalRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Sucursal con ID {} no encontrada", id);
                    return new RuntimeException("Sucursal no encontrada");
                });
    }

    public Sucursal guardar(Sucursal sucursal) {
        Sucursal resultado = sucursalRepository.save(sucursal);
        log.info("Sucursal creada con ID: {} nombre: {}", resultado.getId(), resultado.getNombre());
        return resultado;
    }

    public Sucursal actualizar(Integer id, Sucursal datos) {
        log.info("Actualizando sucursal con ID: {}", id);
        Sucursal sucursal = buscarPorId(id);
        sucursal.setNombre(datos.getNombre());
        sucursal.setDireccion(datos.getDireccion());
        sucursal.setComuna(datos.getComuna());
        sucursal.setCantidadEmpleados(datos.getCantidadEmpleados());
        Sucursal resultado = sucursalRepository.save(sucursal);
        log.info("Sucursal {} actualizada correctamente", id);
        return resultado;
    }

    public void eliminar(Integer id) {
        if (!sucursalRepository.existsById(id)) {
            log.error("No se puede eliminar. Sucursal con ID {} no existe", id);
            throw new RuntimeException("Sucursal no existe");
        }
        sucursalRepository.deleteById(id);
        log.info("Sucursal con ID {} eliminada correctamente", id);
    }
}
