package com.Automotriz.vehiculoMS.repository;

import com.Automotriz.vehiculoMS.model.Mantencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MantencionRepository extends JpaRepository<Mantencion, Integer> {
    List<Mantencion> findByVehiculoId(Integer vehiculoId);
}
