package com.Automotriz.pagosMS.service;

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

import com.Automotriz.pagosMS.model.Pago;
import com.Automotriz.pagosMS.repository.PagoRepository;

@ExtendWith(MockitoExtension.class)
public class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoService pagoService;

    private Pago pagoEjemplo;

    @BeforeEach
    void setUp() {
        pagoEjemplo = new Pago();
        pagoEjemplo.setId(1);
        pagoEjemplo.setReservaId(1);
        pagoEjemplo.setMonto(50000.0);
        pagoEjemplo.setFechaPago("2024-01-01");
        pagoEjemplo.setEstadoPago("PENDIENTE");
        pagoEjemplo.setMetodoPago("TARJETA");
    }

    @Test
    void listar_retornaListaConPagos() {
        List<Pago> listaFalsa = new ArrayList<>();
        listaFalsa.add(pagoEjemplo);
        when(pagoRepository.findAll()).thenReturn(listaFalsa);

        List<Pago> resultado = pagoService.listar();

        assertEquals(1, resultado.size());
        assertEquals(50000.0, resultado.get(0).getMonto());
    }

    @Test
    void buscarPorId_encontrado() {
        when(pagoRepository.findById(1)).thenReturn(Optional.of(pagoEjemplo));

        Pago resultado = pagoService.buscarPorId(1);

        assertEquals(1, resultado.getId());
        assertEquals("PENDIENTE", resultado.getEstadoPago());
    }

    @Test
    void buscarPorId_noEncontrado() {
        when(pagoRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            pagoService.buscarPorId(99);
        });

        assertEquals("Pago no encontrado", ex.getMessage());
    }

    @Test
    void guardar_retornaPagoGuardado() {
        when(pagoRepository.save(pagoEjemplo)).thenReturn(pagoEjemplo);

        Pago resultado = pagoService.guardar(pagoEjemplo);

        assertEquals(50000.0, resultado.getMonto());
        verify(pagoRepository, times(1)).save(pagoEjemplo);
    }

    @Test
    void eliminar_exitoso() {
        when(pagoRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> pagoService.eliminar(1));
        verify(pagoRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noExiste() {
        when(pagoRepository.existsById(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            pagoService.eliminar(99);
        });

        assertEquals("Pago no existe", ex.getMessage());
        verify(pagoRepository, never()).deleteById(99);
    }
}
