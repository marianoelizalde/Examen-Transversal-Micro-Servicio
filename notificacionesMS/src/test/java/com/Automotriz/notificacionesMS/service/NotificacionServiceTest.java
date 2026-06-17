package com.Automotriz.notificacionesMS.service;

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

import com.Automotriz.notificacionesMS.model.Notificacion;
import com.Automotriz.notificacionesMS.repository.NotificacionRepository;

@ExtendWith(MockitoExtension.class)
public class NotificacionServiceTest {

    @Mock
    private NotificacionRepository nnotificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    private Notificacion notificacionEjemplo;

    @BeforeEach
    void setUp() {
        notificacionEjemplo = new Notificacion();
        notificacion.setId(1);
        notificacion.setTipo("EMAIL");
        notificacion.setReservaId(1);
        notificacion.setMensaje("Notificación de prueba");
        notificacion.setFechaNotificacion("2024-01-01");
        notificacion.setEstado("PENDIENTE");
    }

    @Test
    void listar_retornaListaConNotificacions() {
        List<Notificacion> listaFalsa = new ArrayList<>();
        listaFalsa.add(notificacionEjemplo);
        when(nnotificacionRepository.findAll()).thenReturn(listaFalsa);

        List<Notificacion> resultado = notificacionService.listar();

        assertEquals(1, resultado.size());
    }

    @Test
    void buscarPorId_encontrado() {
        when(nnotificacionRepository.findById(1)).thenReturn(Optional.of(notificacionEjemplo));

        Notificacion resultado = notificacionService.buscarPorId(1);

        assertEquals(1, resultado.getId());
    }

    @Test
    void buscarPorId_noEncontrado() {
        when(nnotificacionRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            notificacionService.buscarPorId(99);
        });

        assertEquals("Notificación no encontrada", ex.getMessage());
    }

    @Test
    void guardar_retornaNotificacionGuardado() {
        when(nnotificacionRepository.save(notificacionEjemplo)).thenReturn(notificacionEjemplo);

        Notificacion resultado = notificacionService.guardar(notificacionEjemplo);

        assertNotNull(resultado);
        verify(nnotificacionRepository, times(1)).save(notificacionEjemplo);
    }

    @Test
    void eliminar_exitoso() {
        when(nnotificacionRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> notificacionService.eliminar(1));
        verify(nnotificacionRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noExiste() {
        when(nnotificacionRepository.existsById(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            notificacionService.eliminar(99);
        });

        assertEquals("Notificación no existe", ex.getMessage());
        verify(nnotificacionRepository, never()).deleteById(99);
    }
}
