package com.Automotriz.usuarioMS.repository;

import com.Automotriz.usuarioMS.model.Antecedentes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AntecedentesRepository extends JpaRepository<Antecedentes, Integer> {
    Optional<Antecedentes> findByClienteId(Integer clienteId);
}
