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

    @Autowired
    private NotificacionRepository notificacionRepository;

    public List<Notificacion> listar() {
        log.info("Listando todas las notificaciones");
        return notificacionRepository.findAll();
    }

    public Notificacion buscarPorId(Integer id) {
        log.info("Buscando notificacion con id: {}", id);
        return notificacionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Notificacion con id {} no encontrada", id);
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

    public List<Notificacion> buscarPorTipo(String tipo) {
        log.info("Buscando notificaciones de tipo: {}", tipo);
        return notificacionRepository.findByTipo(tipo);
    }

    public Notificacion guardar(Notificacion notificacion) {
        // REGLA DE NEGOCIO: el mensaje no puede estar vacío
        if (notificacion.getMensaje() == null || notificacion.getMensaje().isBlank()) {
            log.error("Mensaje invalido al crear notificacion: {}", notificacion.getMensaje());
            throw new RuntimeException("El mensaje no puede estar vacio");
        }
        notificacion.setEstado("PENDIENTE");
        Notificacion resultado = notificacionRepository.save(notificacion);
        log.info("Notificacion creada con id: {} para reserva: {}", resultado.getId(), resultado.getReservaId());
        return resultado;
    }

    public Notificacion actualizar(Integer id, Notificacion datos) {
        log.info("Actualizando notificacion con id: {}", id);
        Notificacion notificacion = buscarPorId(id);
        notificacion.setTipo(datos.getTipo());
        notificacion.setReservaId(datos.getReservaId());
        notificacion.setMensaje(datos.getMensaje());
        notificacion.setFechaNotificacion(datos.getFechaNotificacion());
        notificacion.setEstado(datos.getEstado());
        Notificacion resultado = notificacionRepository.save(notificacion);
        log.info("Notificacion {} actualizada correctamente", id);
        return resultado;
    }

    public void eliminar(Integer id) {
        if (!notificacionRepository.existsById(id)) {
            log.error("No se puede eliminar. Notificacion con id {} no existe", id);
            throw new RuntimeException("Notificacion no existe");
        }
        notificacionRepository.deleteById(id);
        log.info("Notificacion con id {} eliminada correctamente", id);
    }
}
