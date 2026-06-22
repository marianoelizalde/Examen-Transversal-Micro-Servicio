package com.Automotriz.documentosMS.service;

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

import com.Automotriz.documentosMS.model.Contrato;
import com.Automotriz.documentosMS.repository.ContratoRepository;

@ExtendWith(MockitoExtension.class)
public class ContratoServiceTest {

    @Mock
    private ContratoRepository contratoRepository;

    @InjectMocks
    private ContratoService contratoService;

    private Contrato contratoEjemplo;

    @BeforeEach
    void setUp() {
        contratoEjemplo = new Contrato();
        contratoEjemplo.setId(1);
        contratoEjemplo.setReservaId(1);
        contratoEjemplo.setFechaEmision("2024-01-01");
        contratoEjemplo.setEstado("PENDIENTE");
        contratoEjemplo.setClausulas("Clausulas del contrato de arrendamiento vehicular");
    }

    // TEST QUE PASA - listar retorna contratos
    @Test
    @DisplayName("listar - retorna lista con contratos")
    void listar_retornaListaConContratos() {
        List<Contrato> listaFalsa = new ArrayList<>();
        listaFalsa.add(contratoEjemplo);
        when(contratoRepository.findAll()).thenReturn(listaFalsa);

        List<Contrato> resultado = contratoService.listar();

        assertEquals(1, resultado.size());
        assertEquals("PENDIENTE", resultado.get(0).getEstado());
    }

    // TEST QUE PASA - buscar por ID existente
    @Test
    @DisplayName("buscar por id - encontrado")
    void buscarPorId_encontrado() {
        when(contratoRepository.findById(1)).thenReturn(Optional.of(contratoEjemplo));

        Contrato resultado = contratoService.buscarPorId(1);

        assertEquals(1, resultado.getId());
        assertEquals("PENDIENTE", resultado.getEstado());
    }

    // TEST QUE NO PASA - buscar por ID inexistente
    @Test
    @DisplayName("buscar por id - no encontrado")
    void buscarPorId_noEncontrado() {
        when(contratoRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            contratoService.buscarPorId(99);
        });

        assertEquals("Contrato no encontrado", ex.getMessage());
    }

    // TEST QUE PASA - buscar por reserva
    @Test
    @DisplayName("buscar por reserva - retorna contratos")
    void buscarPorReserva_retornaContratos() {
        List<Contrato> lista = new ArrayList<>();
        lista.add(contratoEjemplo);
        when(contratoRepository.findByReservaId(1)).thenReturn(lista);

        List<Contrato> resultado = contratoService.buscarPorReserva(1);

        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getReservaId());
    }

    // TEST QUE PASA - guardar contrato
    @Test
    @DisplayName("guardar - retorna contrato guardado")
    void guardar_retornaContratoGuardado() {
        when(contratoRepository.save(any(Contrato.class))).thenReturn(contratoEjemplo);

        Contrato resultado = contratoService.guardar(contratoEjemplo);

        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstado());
        verify(contratoRepository, times(1)).save(any(Contrato.class));
    }

    // TEST QUE PASA - eliminar existente
    @Test
    @DisplayName("eliminar - exitoso")
    void eliminar_exitoso() {
        when(contratoRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> contratoService.eliminar(1));
        verify(contratoRepository, times(1)).deleteById(1);
    }

    // TEST QUE NO PASA - eliminar inexistente
    @Test
    @DisplayName("eliminar - no existe")
    void eliminar_noExiste() {
        when(contratoRepository.existsById(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            contratoService.eliminar(99);
        });

        assertEquals("Contrato no existe", ex.getMessage());
        verify(contratoRepository, never()).deleteById(99);
    }
}
