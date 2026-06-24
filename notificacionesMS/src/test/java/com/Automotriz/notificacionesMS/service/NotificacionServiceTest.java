package com.Automotriz.notificacionesMS.service;

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

import com.Automotriz.notificacionesMS.model.Notificacion;
import com.Automotriz.notificacionesMS.repository.NotificacionRepository;

@ExtendWith(MockitoExtension.class)
public class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    private Notificacion notificacionEjemplo;

    @BeforeEach
    void setUp() {
        notificacionEjemplo = new Notificacion();
        notificacionEjemplo.setId(1);
        notificacionEjemplo.setTipo("EMAIL");
        notificacionEjemplo.setReservaId(1);
        notificacionEjemplo.setMensaje("Su reserva ha sido confirmada");
        notificacionEjemplo.setFechaNotificacion("2024-01-01");
        notificacionEjemplo.setEstado("PENDIENTE");
    }

    // TEST QUE PASA - listar retorna notificaciones
    @Test
    @DisplayName("listar - retorna lista con notificaciones")
    void listar_retornaListaConNotificaciones() {
        List<Notificacion> listaFalsa = new ArrayList<>();
        listaFalsa.add(notificacionEjemplo);
        when(notificacionRepository.findAll()).thenReturn(listaFalsa);

        List<Notificacion> resultado = notificacionService.listar();

        assertEquals(1, resultado.size());
        assertEquals("EMAIL", resultado.get(0).getTipo());
    }

    // TEST QUE PASA - buscar por ID existente
    @Test
    @DisplayName("buscar por id - encontrado")
    void buscarPorId_encontrado() {
        when(notificacionRepository.findById(1)).thenReturn(Optional.of(notificacionEjemplo));

        Notificacion resultado = notificacionService.buscarPorId(1);

        assertEquals(1, resultado.getId());
        assertEquals("PENDIENTE", resultado.getEstado());
    }

    // TEST QUE NO PASA - buscar por ID inexistente
    @Test
    @DisplayName("buscar por id - no encontrado")
    void buscarPorId_noEncontrado() {
        when(notificacionRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            notificacionService.buscarPorId(99);
        });

        assertEquals("Notificacion no encontrada", ex.getMessage());
    }

    // TEST QUE PASA - buscar por reserva
    @Test
    @DisplayName("buscar por reserva - retorna notificaciones")
    void buscarPorReserva_retornaNotificaciones() {
        List<Notificacion> lista = new ArrayList<>();
        lista.add(notificacionEjemplo);
        when(notificacionRepository.findByReservaId(1)).thenReturn(lista);

        List<Notificacion> resultado = notificacionService.buscarPorReserva(1);

        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getReservaId());
    }

    // TEST QUE PASA - buscar por estado
    @Test
    @DisplayName("buscar por estado - retorna notificaciones")
    void buscarPorEstado_retornaNotificaciones() {
        List<Notificacion> lista = new ArrayList<>();
        lista.add(notificacionEjemplo);
        when(notificacionRepository.findByEstado("PENDIENTE")).thenReturn(lista);

        List<Notificacion> resultado = notificacionService.buscarPorEstado("PENDIENTE");

        assertEquals(1, resultado.size());
        assertEquals("PENDIENTE", resultado.get(0).getEstado());
    }

    // TEST QUE PASA - buscar por tipo
    @Test
    @DisplayName("buscar por tipo - retorna notificaciones")
    void buscarPorTipo_retornaNotificaciones() {
        List<Notificacion> lista = new ArrayList<>();
        lista.add(notificacionEjemplo);
        when(notificacionRepository.findByTipo("EMAIL")).thenReturn(lista);

        List<Notificacion> resultado = notificacionService.buscarPorTipo("EMAIL");

        assertEquals(1, resultado.size());
        assertEquals("EMAIL", resultado.get(0).getTipo());
    }

    // TEST QUE PASA - guardar con mensaje valido
    @Test
    @DisplayName("guardar - mensaje valido - retorna notificacion")
    void guardar_mensajeValido_retornaNotificacion() {
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacionEjemplo);

        Notificacion resultado = notificacionService.guardar(notificacionEjemplo);

        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstado());
        verify(notificacionRepository, times(1)).save(any(Notificacion.class));
    }

    // TEST QUE NO PASA - guardar con mensaje vacio lanza excepcion
    @Test
    @DisplayName("guardar - mensaje vacio - lanza excepcion")
    void guardar_mensajeVacio_lanzaExcepcion() {
        notificacionEjemplo.setMensaje("");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            notificacionService.guardar(notificacionEjemplo);
        });

        assertEquals("El mensaje no puede estar vacio", ex.getMessage());
        verify(notificacionRepository, never()).save(any());
    }

    // TEST QUE NO PASA - guardar con mensaje nulo lanza excepcion
    @Test
    @DisplayName("guardar - mensaje nulo - lanza excepcion")
    void guardar_mensajeNulo_lanzaExcepcion() {
        notificacionEjemplo.setMensaje(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            notificacionService.guardar(notificacionEjemplo);
        });

        assertEquals("El mensaje no puede estar vacio", ex.getMessage());
        verify(notificacionRepository, never()).save(any());
    }

    // TEST QUE PASA - actualizar notificacion existente
    @Test
    @DisplayName("actualizar - existente - retorna notificacion actualizada")
    void actualizar_existente_retornaNotificacionActualizada() {
        Notificacion datosNuevos = new Notificacion();
        datosNuevos.setTipo("SMS");
        datosNuevos.setReservaId(1);
        datosNuevos.setMensaje("Recordatorio de devolucion");
        datosNuevos.setFechaNotificacion("2024-01-05");
        datosNuevos.setEstado("ENVIADA");

        when(notificacionRepository.findById(1)).thenReturn(Optional.of(notificacionEjemplo));
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacionEjemplo);

        Notificacion resultado = notificacionService.actualizar(1, datosNuevos);

        assertNotNull(resultado);
        verify(notificacionRepository, times(1)).save(any(Notificacion.class));
    }

    // TEST QUE PASA - eliminar existente
    @Test
    @DisplayName("eliminar - exitoso")
    void eliminar_exitoso() {
        when(notificacionRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> notificacionService.eliminar(1));
        verify(notificacionRepository, times(1)).deleteById(1);
    }

    // TEST QUE NO PASA - eliminar inexistente
    @Test
    @DisplayName("eliminar - no existe")
    void eliminar_noExiste() {
        when(notificacionRepository.existsById(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            notificacionService.eliminar(99);
        });

        assertEquals("Notificacion no existe", ex.getMessage());
        verify(notificacionRepository, never()).deleteById(99);
    }
}
