package com.Automotriz.soporteMS.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Automotriz.soporteMS.model.Ticket;
import com.Automotriz.soporteMS.repository.TicketRepository;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticketEjemplo;

    @BeforeEach
    void setUp() {
        ticketEjemplo = new Ticket();
        ticketEjemplo.setId(1);
        ticketEjemplo.setReservaId(1);
        ticketEjemplo.setAsunto("Problema con la reserva");
        ticketEjemplo.setDescripcion("Descripcion detallada del problema");
        ticketEjemplo.setEstado("ABIERTO");
        ticketEjemplo.setFechaCreacion("2024-01-01");
    }

    // TEST QUE PASA - listar retorna tickets
    @Test
    @DisplayName("listar - retorna lista con tickets")
    void listar_retornaListaConTickets() {
        List<Ticket> lista = new ArrayList<>();
        lista.add(ticketEjemplo);
        when(ticketRepository.findAll()).thenReturn(lista);

        List<Ticket> resultado = ticketService.listar();

        assertEquals(1, resultado.size());
        assertEquals("ABIERTO", resultado.get(0).getEstado());
    }

    // TEST QUE PASA - buscar por ID existente
    @Test
    @DisplayName("buscar por id - encontrado")
    void buscarPorId_encontrado() {
        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticketEjemplo));

        Ticket resultado = ticketService.buscarPorId(1);

        assertEquals(1, resultado.getId());
        assertEquals("ABIERTO", resultado.getEstado());
    }

    // TEST QUE NO PASA - buscar por ID inexistente
    @Test
    @DisplayName("buscar por id - no encontrado")
    void buscarPorId_noEncontrado() {
        when(ticketRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            ticketService.buscarPorId(99);
        });

        assertEquals("Ticket no encontrado", ex.getMessage());
    }

    // TEST QUE PASA - buscar por reserva
    @Test
    @DisplayName("buscar por reserva - retorna tickets")
    void buscarPorReserva_retornaTickets() {
        List<Ticket> lista = new ArrayList<>();
        lista.add(ticketEjemplo);
        when(ticketRepository.findByReservaId(1)).thenReturn(lista);

        List<Ticket> resultado = ticketService.buscarPorReserva(1);

        assertEquals(1, resultado.size());
    }

    // TEST QUE PASA - guardar ticket
    @Test
    @DisplayName("guardar - retorna ticket guardado")
    void guardar_retornaTicketGuardado() {
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticketEjemplo);

        Ticket resultado = ticketService.guardar(ticketEjemplo);

        assertNotNull(resultado);
        assertEquals("ABIERTO", resultado.getEstado());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    // TEST QUE PASA - eliminar existente
    @Test
    @DisplayName("eliminar - exitoso")
    void eliminar_exitoso() {
        when(ticketRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> ticketService.eliminar(1));
        verify(ticketRepository, times(1)).deleteById(1);
    }

    // TEST QUE NO PASA - eliminar inexistente
    @Test
    @DisplayName("eliminar - no existe")
    void eliminar_noExiste() {
        when(ticketRepository.existsById(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            ticketService.eliminar(99);
        });

        assertEquals("Ticket no existe", ex.getMessage());
        verify(ticketRepository, never()).deleteById(99);
    }
}
