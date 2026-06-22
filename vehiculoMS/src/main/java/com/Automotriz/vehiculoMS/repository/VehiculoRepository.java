package com.Automotriz.vehiculoMS.repository;

import com.Automotriz.vehiculoMS.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    Optional<Vehiculo> findByPatente(String patente);
    List<Vehiculo> findByEstado(String estado);
}
