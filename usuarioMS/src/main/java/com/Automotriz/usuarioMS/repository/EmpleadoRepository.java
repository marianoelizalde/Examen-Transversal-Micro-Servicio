package com.Automotriz.usuarioMS.repository;

import com.Automotriz.usuarioMS.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Optional<Empleado> findByUsuarioId(Integer usuarioId);
    List<Empleado> findBySucursalId(Integer sucursalId);
}
