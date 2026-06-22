package com.Automotriz.usuarioMS.service;

import com.Automotriz.usuarioMS.model.Empleado;
import com.Automotriz.usuarioMS.repository.EmpleadoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> listar() {
        log.info("Listando todos los empleados");
        return empleadoRepository.findAll();
    }

    public Empleado buscarPorId(Integer id) {
        log.info("Buscando empleado con ID: {}", id);
        return empleadoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Empleado con ID {} no encontrado", id);
                    return new RuntimeException("Empleado no encontrado");
                });
    }

    public Empleado buscarPorUsuarioId(Integer usuarioId) {
        log.info("Buscando empleado con usuarioId: {}", usuarioId);
        return empleadoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> {
                    log.error("Empleado con usuarioId {} no encontrado", usuarioId);
                    return new RuntimeException("Empleado no encontrado");
                });
    }

    public List<Empleado> buscarPorSucursal(Integer sucursalId) {
        log.info("Buscando empleados de la sucursal: {}", sucursalId);
        return empleadoRepository.findBySucursalId(sucursalId);
    }

    public Empleado guardar(Empleado empleado) {
        Empleado resultado = empleadoRepository.save(empleado);
        log.info("Empleado creado con ID: {}", resultado.getId());
        return resultado;
    }

    public Empleado actualizar(Integer id, Empleado datos) {
        log.info("Actualizando empleado con ID: {}", id);
        Empleado empleado = buscarPorId(id);
        empleado.setDescripcion(datos.getDescripcion());
        empleado.setSucursalId(datos.getSucursalId());
        empleado.setInfoBancaria(datos.getInfoBancaria());
        Empleado resultado = empleadoRepository.save(empleado);
        log.info("Empleado {} actualizado correctamente", id);
        return resultado;
    }

    public void eliminar(Integer id) {
        if (!empleadoRepository.existsById(id)) {
            log.error("No se puede eliminar. Empleado con ID {} no existe", id);
            throw new RuntimeException("Empleado no existe");
        }
        empleadoRepository.deleteById(id);
        log.info("Empleado con ID {} eliminado correctamente", id);
    }
}
