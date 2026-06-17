package com.Automotriz.notificacionesMS.repository;

import com.Automotriz.notificacionesMS.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    List<Notificacion> findByReservaId(Integer reservaId);
    List<Notificacion> findByEstado(String estado);
    List<Notificacion> findByTipo(String tipo);
}
