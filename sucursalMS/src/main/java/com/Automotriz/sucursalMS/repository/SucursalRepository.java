package com.Automotriz.sucursalMS.repository;

import com.Automotriz.sucursalMS.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {
    List<Sucursal> findByComuna(String comuna);
}
