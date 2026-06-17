package com.Automotriz.documentosMS.service;

import com.Automotriz.documentosMS.model.Participante;
import com.Automotriz.documentosMS.repository.ParticipanteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;

    public List<Participante> listar() {
        log.info("Listando todos los participantes");
        return participanteRepository.findAll();
    }

    public Participante buscarPorId(Integer id) {
        log.info("Buscando participante con ID: {}", id);
        return participanteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Participante con ID {} no encontrado", id);
                    return new RuntimeException("Participante no encontrado");
                });
    }

    public List<Participante> buscarPorContrato(Integer contratoId) {
        log.info("Buscando participantes del contrato: {}", contratoId);
        return participanteRepository.findByContratoId(contratoId);
    }

    public Participante guardar(Participante participante) {
        Participante resultado = participanteRepository.save(participante);
        log.info("Participante creado con ID: {} rol: {}", resultado.getId(), resultado.getRol());
        return resultado;
    }

    public void eliminar(Integer id) {
        if (!participanteRepository.existsById(id)) {
            log.error("No se puede eliminar. Participante con ID {} no existe", id);
            throw new RuntimeException("Participante no existe");
        }
        participanteRepository.deleteById(id);
        log.info("Participante con ID {} eliminado correctamente", id);
    }
}
