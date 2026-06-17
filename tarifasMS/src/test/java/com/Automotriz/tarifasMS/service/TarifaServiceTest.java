package com.Automotriz.tarifasMS.service;

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

import com.Automotriz.tarifasMS.model.Tarifa;
import com.Automotriz.tarifasMS.repository.TarifaRepository;

@ExtendWith(MockitoExtension.class)
public class TarifaServiceTest {

    @Mock
    private TarifaRepository ttarifaRepository;

    @InjectMocks
    private TarifaService tarifaService;

    private Tarifa tarifaEjemplo;

    @BeforeEach
    void setUp() {
        tarifaEjemplo = new Tarifa();
        tarifa.setId(1);
        tarifa.setVehiculoId(1);
        tarifa.setPrecioDia(50000.0);
        tarifa.setTemporada("NORMAL");
        tarifa.setEstado("ACTIVO");
    }

    @Test
    void listar_retornaListaConTarifas() {
        List<Tarifa> listaFalsa = new ArrayList<>();
        listaFalsa.add(tarifaEjemplo);
        when(ttarifaRepository.findAll()).thenReturn(listaFalsa);

        List<Tarifa> resultado = tarifaService.listar();

        assertEquals(1, resultado.size());
    }

    @Test
    void buscarPorId_encontrado() {
        when(ttarifaRepository.findById(1)).thenReturn(Optional.of(tarifaEjemplo));

        Tarifa resultado = tarifaService.buscarPorId(1);

        assertEquals(1, resultado.getId());
    }

    @Test
    void buscarPorId_noEncontrado() {
        when(ttarifaRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            tarifaService.buscarPorId(99);
        });

        assertEquals("Tarifa no encontrada", ex.getMessage());
    }

    @Test
    void guardar_retornaTarifaGuardado() {
        when(ttarifaRepository.save(tarifaEjemplo)).thenReturn(tarifaEjemplo);

        Tarifa resultado = tarifaService.guardar(tarifaEjemplo);

        assertNotNull(resultado);
        verify(ttarifaRepository, times(1)).save(tarifaEjemplo);
    }

    @Test
    void eliminar_exitoso() {
        when(ttarifaRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> tarifaService.eliminar(1));
        verify(ttarifaRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noExiste() {
        when(ttarifaRepository.existsById(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            tarifaService.eliminar(99);
        });

        assertEquals("Tarifa no existe", ex.getMessage());
        verify(ttarifaRepository, never()).deleteById(99);
    }
}
