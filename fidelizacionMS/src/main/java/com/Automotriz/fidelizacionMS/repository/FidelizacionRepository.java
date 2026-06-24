package com.Automotriz.fidelizacionMS.repository;

import com.Automotriz.fidelizacionMS.model.Fidelizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FidelizacionRepository extends JpaRepository<Fidelizacion, Integer> {
    Optional<Fidelizacion> findByRutCliente(String rutCliente);
    List<Fidelizacion> findByNivelSocio(String nivelSocio);
}
