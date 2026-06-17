package com.Automotriz.reservasMS.service;

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

import com.Automotriz.reservasMS.model.Reserva;
import com.Automotriz.reservasMS.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    private Reserva reservaEjemplo;

    @BeforeEach
    void setUp() {
        reservaEjemplo = new Reserva();
        reservaEjemplo.setId(1);
        reservaEjemplo.setRutCliente("12345678-9");
        reservaEjemplo.setPatente("ABCD12");
        reservaEjemplo.setSucursalId(1);
        reservaEjemplo.setFechaInicio("2024-01-01");
        reservaEjemplo.setFechaFin("2024-01-10");
        reservaEjemplo.setEstado("PENDIENTE");
    }

    @Test
    void listar_retornaListaConReservas() {
        List<Reserva> listaFalsa = new ArrayList<>();
        listaFalsa.add(reservaEjemplo);
        when(reservaRepository.findAll()).thenReturn(listaFalsa);

        List<Reserva> resultado = reservaService.listar();

        assertEquals(1, resultado.size());
        assertEquals("12345678-9", resultado.get(0).getRutCliente());
    }

    @Test
    void buscarPorId_encontrado() {
        when(reservaRepository.findById(1)).thenReturn(Optional.of(reservaEjemplo));

        Reserva resultado = reservaService.buscarPorId(1);

        assertEquals(1, resultado.getId());
        assertEquals("PENDIENTE", resultado.getEstado());
    }

    @Test
    void buscarPorId_noEncontrado() {
        when(reservaRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reservaService.buscarPorId(99);
        });

        assertEquals("Reserva no encontrada", ex.getMessage());
    }

    @Test
    void eliminar_exitoso() {
        when(reservaRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> reservaService.eliminar(1));
        verify(reservaRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noExiste() {
        when(reservaRepository.existsById(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reservaService.eliminar(99);
        });

        assertEquals("Reserva no existe", ex.getMessage());
        verify(reservaRepository, never()).deleteById(99);
    }
}
