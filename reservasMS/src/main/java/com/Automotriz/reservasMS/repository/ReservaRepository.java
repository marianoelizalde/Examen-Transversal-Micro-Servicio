package com.Automotriz.reservasMS.repository;

import com.Automotriz.reservasMS.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByRutCliente(String rutCliente);
    List<Reserva> findByPatente(String patente);
    List<Reserva> findBySucursalId(Integer sucursalId);
    List<Reserva> findByEstado(String estado);

    // Verifica si existe una reserva activa para un vehículo específico
    boolean existsByPatenteAndEstado(String patente, String estado);
}
