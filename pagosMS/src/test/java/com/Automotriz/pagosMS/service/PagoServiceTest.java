package com.Automotriz.pagosMS.service;

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

    // TEST QUE PASA - listar retorna pagos
    @Test
    @DisplayName("listar - retorna lista con pagos")
    void listar_retornaListaConPagos() {
        List<Pago> listaFalsa = new ArrayList<>();
        listaFalsa.add(pagoEjemplo);
        when(pagoRepository.findAll()).thenReturn(listaFalsa);

        List<Pago> resultado = pagoService.listar();

        assertEquals(1, resultado.size());
        assertEquals(50000.0, resultado.get(0).getMonto());
    }

    // TEST QUE PASA - buscar por ID existente
    @Test
    @DisplayName("buscar por id - encontrado")
    void buscarPorId_encontrado() {
        when(pagoRepository.findById(1)).thenReturn(Optional.of(pagoEjemplo));

        Pago resultado = pagoService.buscarPorId(1);

        assertEquals(1, resultado.getId());
        assertEquals("PENDIENTE", resultado.getEstadoPago());
    }

    // TEST QUE NO PASA - buscar por ID inexistente
    @Test
    @DisplayName("buscar por id - no encontrado")
    void buscarPorId_noEncontrado() {
        when(pagoRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            pagoService.buscarPorId(99);
        });

        assertEquals("Pago no encontrado", ex.getMessage());
    }

    // TEST QUE PASA - buscar por reserva
    @Test
    @DisplayName("buscar por reserva - retorna pagos")
    void buscarPorReserva_retornaPagos() {
        List<Pago> lista = new ArrayList<>();
        lista.add(pagoEjemplo);
        when(pagoRepository.findByReservaId(1)).thenReturn(lista);

        List<Pago> resultado = pagoService.buscarPorReserva(1);

        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getReservaId());
    }

    // TEST QUE PASA - guardar con monto valido
    @Test
    @DisplayName("guardar - monto valido - retorna pago")
    void guardar_montoValido_retornaPago() {
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoEjemplo);

        Pago resultado = pagoService.guardar(pagoEjemplo);

        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstadoPago());
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }

    // TEST QUE NO PASA - guardar con monto invalido lanza excepcion
    @Test
    @DisplayName("guardar - monto invalido - lanza excepcion")
    void guardar_montoInvalido_lanzaExcepcion() {
        pagoEjemplo.setMonto(-100.0);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            pagoService.guardar(pagoEjemplo);
        });

        assertEquals("El monto debe ser mayor a 0", ex.getMessage());
        verify(pagoRepository, never()).save(any());
    }

    // TEST QUE PASA - eliminar existente
    @Test
    @DisplayName("eliminar - exitoso")
    void eliminar_exitoso() {
        when(pagoRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> pagoService.eliminar(1));
        verify(pagoRepository, times(1)).deleteById(1);
    }

    // TEST QUE NO PASA - eliminar inexistente
    @Test
    @DisplayName("eliminar - no existe")
    void eliminar_noExiste() {
        when(pagoRepository.existsById(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            pagoService.eliminar(99);
        });

        assertEquals("Pago no existe", ex.getMessage());
        verify(pagoRepository, never()).deleteById(99);
    }
}
