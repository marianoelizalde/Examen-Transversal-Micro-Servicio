package com.Automotriz.soporteMS.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Automotriz.soporteMS.model.Ticket;
import com.Automotriz.soporteMS.repository.TicketRepository;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository tticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticketEjemplo;

    @BeforeEach
    void setUp() {
        ticketEjemplo = new Ticket();
        ticket.setId(1);
        ticket.setReservaId(1);
        ticket.setAsunto("Problema con la reserva");
        ticket.setDescripcion("Descripcion detallada del problema");
        ticket.setEstado("ABIERTO");
        ticket.setFechaCreacion("2024-01-01");
    }

    @Test
    void listar_retornaListaConTickets() {
        List<Ticket> listaFalsa = new ArrayList<>();
        listaFalsa.add(ticketEjemplo);
        when(tticketRepository.findAll()).thenReturn(listaFalsa);

        List<Ticket> resultado = ticketService.listar();

        assertEquals(1, resultado.size());
    }

    @Test
    void buscarPorId_encontrado() {
        when(tticketRepository.findById(1)).thenReturn(Optional.of(ticketEjemplo));

        Ticket resultado = ticketService.buscarPorId(1);

        assertEquals(1, resultado.getId());
    }

    @Test
    void buscarPorId_noEncontrado() {
        when(tticketRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            ticketService.buscarPorId(99);
        });

        assertEquals("Ticket no encontrado", ex.getMessage());
    }

    @Test
    void guardar_retornaTicketGuardado() {
        when(tticketRepository.save(ticketEjemplo)).thenReturn(ticketEjemplo);

        Ticket resultado = ticketService.guardar(ticketEjemplo);

        assertNotNull(resultado);
        verify(tticketRepository, times(1)).save(ticketEjemplo);
    }

    @Test
    void eliminar_exitoso() {
        when(tticketRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> ticketService.eliminar(1));
        verify(tticketRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noExiste() {
        when(tticketRepository.existsById(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            ticketService.eliminar(99);
        });

        assertEquals("Ticket no existe", ex.getMessage());
        verify(tticketRepository, never()).deleteById(99);
    }
}
