package com.Automotriz.documentosMS.repository;

import com.Automotriz.documentosMS.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Integer> {
    List<Contrato> findByReservaId(Integer reservaId);
    List<Contrato> findByEstado(String estado);
}
