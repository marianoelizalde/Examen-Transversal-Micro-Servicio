package com.Automotriz.soporteMS.service;

import com.Automotriz.soporteMS.model.Ticket;
import com.Automotriz.soporteMS.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class TicketService {

    @Autowired private TicketRepository ticketRepository;

    public List<Ticket> listar() {
        log.info("Listando todos los tickets");
        return ticketRepository.findAll();
    }

    public Ticket buscarPorId(Integer id) {
        log.info("Buscando ticket con ID: {}", id);
        return ticketRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Ticket con ID {} no encontrado", id);
                    return new RuntimeException("Ticket no encontrado");
                });
    }

    public List<Ticket> buscarPorReserva(Integer reservaId) {
        log.info("Buscando tickets de la reserva: {}", reservaId);
        return ticketRepository.findByReservaId(reservaId);
    }

    public List<Ticket> buscarPorEstado(String estado) {
        log.info("Buscando tickets con estado: {}", estado);
        return ticketRepository.findByEstado(estado);
    }

    public Ticket guardar(Ticket ticket) {
        ticket.setEstado("ABIERTO");
        ticket.setFechaCreacion("2026-05-12");
        Ticket resultado = ticketRepository.save(ticket);
        log.info("Ticket creado con ID: {} para reserva: {}", resultado.getId(), resultado.getReservaId());
        return resultado;
    }

    public Ticket actualizar(Integer id, Ticket datos) {
        log.info("Actualizando ticket con ID: {}", id);
        Ticket ticket = buscarPorId(id);
        ticket.setAsunto(datos.getAsunto());
        ticket.setDescripcion(datos.getDescripcion());
        ticket.setEstado(datos.getEstado());
        Ticket resultado = ticketRepository.save(ticket);
        log.info("Ticket {} actualizado correctamente", id);
        return resultado;
    }

    public void eliminar(Integer id) {
        if (!ticketRepository.existsById(id)) {
            log.error("No se puede eliminar. Ticket con ID {} no existe", id);
            throw new RuntimeException("Ticket no existe");
        }
        ticketRepository.deleteById(id);
        log.info("Ticket con ID {} eliminado correctamente", id);
    }
}
