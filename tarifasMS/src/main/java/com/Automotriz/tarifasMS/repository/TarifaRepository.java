package com.Automotriz.tarifasMS.repository;

import com.Automotriz.tarifasMS.model.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Integer> {
    List<Tarifa> findByVehiculoId(Integer vehiculoId);
    List<Tarifa> findByTemporada(String temporada);
    List<Tarifa> findByEstado(String estado);
}