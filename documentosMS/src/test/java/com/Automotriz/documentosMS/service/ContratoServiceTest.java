package com.Automotriz.documentosMS.service;

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

import com.Automotriz.documentosMS.model.Contrato;
import com.Automotriz.documentosMS.repository.ContratoRepository;

@ExtendWith(MockitoExtension.class)
public class ContratoServiceTest {

    @Mock
    private ContratoRepository ccontratoRepository;

    @InjectMocks
    private ContratoService contratoService;

    private Contrato contratoEjemplo;

    @BeforeEach
    void setUp() {
        contratoEjemplo = new Contrato();
        contrato.setId(1);
        contrato.setReservaId(1);
        contrato.setFechaEmision("2024-01-01");
        contrato.setEstado("PENDIENTE");
        contrato.setClausulas("Clausulas del contrato de prueba");
    }

    @Test
    void listar_retornaListaConContratos() {
        List<Contrato> listaFalsa = new ArrayList<>();
        listaFalsa.add(contratoEjemplo);
        when(ccontratoRepository.findAll()).thenReturn(listaFalsa);

        List<Contrato> resultado = contratoService.listar();

        assertEquals(1, resultado.size());
    }

    @Test
    void buscarPorId_encontrado() {
        when(ccontratoRepository.findById(1)).thenReturn(Optional.of(contratoEjemplo));

        Contrato resultado = contratoService.buscarPorId(1);

        assertEquals(1, resultado.getId());
    }

    @Test
    void buscarPorId_noEncontrado() {
        when(ccontratoRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            contratoService.buscarPorId(99);
        });

        assertEquals("Contrato no encontrado", ex.getMessage());
    }

    @Test
    void guardar_retornaContratoGuardado() {
        when(ccontratoRepository.save(contratoEjemplo)).thenReturn(contratoEjemplo);

        Contrato resultado = contratoService.guardar(contratoEjemplo);

        assertNotNull(resultado);
        verify(ccontratoRepository, times(1)).save(contratoEjemplo);
    }

    @Test
    void eliminar_exitoso() {
        when(ccontratoRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> contratoService.eliminar(1));
        verify(ccontratoRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noExiste() {
        when(ccontratoRepository.existsById(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            contratoService.eliminar(99);
        });

        assertEquals("Contrato no existe", ex.getMessage());
        verify(ccontratoRepository, never()).deleteById(99);
    }
}
