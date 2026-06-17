package com.Automotriz.fidelizacionMS.service;

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

import com.Automotriz.fidelizacionMS.model.Fidelizacion;
import com.Automotriz.fidelizacionMS.repository.FidelizacionRepository;

@ExtendWith(MockitoExtension.class)
public class FidelizacionServiceTest {

    @Mock
    private FidelizacionRepository ffidelizacionRepository;

    @InjectMocks
    private FidelizacionService fidelizacionService;

    private Fidelizacion fidelizacionEjemplo;

    @BeforeEach
    void setUp() {
        fidelizacionEjemplo = new Fidelizacion();
        fidelizacion.setId(1);
        fidelizacion.setRutCliente("12345678-9");
        fidelizacion.setPuntosAcumulados(100);
        fidelizacion.setNivelSocio("BRONCE");
    }

    @Test
    void listar_retornaListaConFidelizacions() {
        List<Fidelizacion> listaFalsa = new ArrayList<>();
        listaFalsa.add(fidelizacionEjemplo);
        when(ffidelizacionRepository.findAll()).thenReturn(listaFalsa);

        List<Fidelizacion> resultado = fidelizacionService.listar();

        assertEquals(1, resultado.size());
    }

    @Test
    void buscarPorId_encontrado() {
        when(ffidelizacionRepository.findById(1)).thenReturn(Optional.of(fidelizacionEjemplo));

        Fidelizacion resultado = fidelizacionService.buscarPorId(1);

        assertEquals(1, resultado.getId());
    }

    @Test
    void buscarPorId_noEncontrado() {
        when(ffidelizacionRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            fidelizacionService.buscarPorId(99);
        });

        assertEquals("Perfil de fidelización no encontrado", ex.getMessage());
    }

    @Test
    void guardar_retornaFidelizacionGuardado() {
        when(ffidelizacionRepository.save(fidelizacionEjemplo)).thenReturn(fidelizacionEjemplo);

        Fidelizacion resultado = fidelizacionService.guardar(fidelizacionEjemplo);

        assertNotNull(resultado);
        verify(ffidelizacionRepository, times(1)).save(fidelizacionEjemplo);
    }

    @Test
    void eliminar_exitoso() {
        when(ffidelizacionRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> fidelizacionService.eliminar(1));
        verify(ffidelizacionRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noExiste() {
        when(ffidelizacionRepository.existsById(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            fidelizacionService.eliminar(99);
        });

        assertEquals("Perfil de fidelización no existe", ex.getMessage());
        verify(ffidelizacionRepository, never()).deleteById(99);
    }
}
