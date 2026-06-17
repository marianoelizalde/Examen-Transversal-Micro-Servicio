package com.Automotriz.notificacionesMS.service;

import com.Automotriz.notificacionesMS.model.Notificacion;
import com.Automotriz.notificacionesMS.repository.NotificacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class NotificacionService {

    @Autowired private NotificacionRepository notificacionRepository;

    public List<Notificacion> listar() {
        log.info("Listando todas las notificaciones");
        return notificacionRepository.findAll();
    }

    public Notificacion buscarPorId(Integer id) {
        log.info("Buscando notificación con ID: {}", id);
        return notificacionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Notificación con ID {} no encontrada", id);
                    return new RuntimeException("Notificacion no encontrada");
                });
    }

    public List<Notificacion> buscarPorReserva(Integer reservaId) {
        log.info("Buscando notificaciones de la reserva: {}", reservaId);
        return notificacionRepository.findByReservaId(reservaId);
    }

    public List<Notificacion> buscarPorEstado(String estado) {
        log.info("Buscando notificaciones con estado: {}", estado);
        return notificacionRepository.findByEstado(estado);
    }

    public Notificacion guardar(Notificacion notificacion) {
        notificacion.setEstado("PENDIENTE");
        notificacion.setFechaNotificacion("2026-05-12");
        Notificacion resultado = notificacionRepository.save(notificacion);
        log.info("Notificación creada con ID: {} tipo: {}", resultado.getId(), resultado.getTipo());
        return resultado;
    }

    public Notificacion actualizar(Integer id, Notificacion datos) {
        log.info("Actualizando notificación con ID: {}", id);
        Notificacion notificacion = buscarPorId(id);
        notificacion.setMensaje(datos.getMensaje());
        notificacion.setEstado(datos.getEstado());
        Notificacion resultado = notificacionRepository.save(notificacion);
        log.info("Notificación {} actualizada correctamente", id);
        return resultado;
    }

    public void eliminar(Integer id) {
        if (!notificacionRepository.existsById(id)) {
            log.error("No se puede eliminar. Notificación con ID {} no existe", id);
            throw new RuntimeException("Notificacion no existe");
        }
        notificacionRepository.deleteById(id);
        log.info("Notificación con ID {} eliminada correctamente", id);
    }
}
