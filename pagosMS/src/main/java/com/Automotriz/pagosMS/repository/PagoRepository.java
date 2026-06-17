package com.Automotriz.pagosMS.repository;

import com.Automotriz.pagosMS.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByReservaId(Integer reservaId);
    List<Pago> findByEstadoPago(String estadoPago);
}
