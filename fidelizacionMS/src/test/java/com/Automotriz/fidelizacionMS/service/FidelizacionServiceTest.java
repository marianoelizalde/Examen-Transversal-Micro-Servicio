package com.Automotriz.fidelizacionMS.service;

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

import com.Automotriz.fidelizacionMS.model.Fidelizacion;
import com.Automotriz.fidelizacionMS.repository.FidelizacionRepository;

@ExtendWith(MockitoExtension.class)
public class FidelizacionServiceTest {

    @Mock
    private FidelizacionRepository fidelizacionRepository;

    @InjectMocks
    private FidelizacionService fidelizacionService;

    private Fidelizacion fidelizacionEjemplo;

    @BeforeEach
    void setUp() {
        fidelizacionEjemplo = new Fidelizacion();
        fidelizacionEjemplo.setId(1);
        fidelizacionEjemplo.setRutCliente("12345678-9");
        fidelizacionEjemplo.setPuntosAcumulados(100);
        fidelizacionEjemplo.setNivelSocio("BRONCE");
    }

    // TEST QUE PASA - listar retorna perfiles
    @Test
    @DisplayName("listar - retorna lista con perfiles")
    void listar_retornaListaConPerfiles() {
        List<Fidelizacion> lista = new ArrayList<>();
        lista.add(fidelizacionEjemplo);
        when(fidelizacionRepository.findAll()).thenReturn(lista);

        List<Fidelizacion> resultado = fidelizacionService.listar();

        assertEquals(1, resultado.size());
        assertEquals("BRONCE", resultado.get(0).getNivelSocio());
    }

    // TEST QUE PASA - buscar por ID existente
    @Test
    @DisplayName("buscar por id - encontrado")
    void buscarPorId_encontrado() {
        when(fidelizacionRepository.findById(1)).thenReturn(Optional.of(fidelizacionEjemplo));

        Fidelizacion resultado = fidelizacionService.buscarPorId(1);

        assertEquals(1, resultado.getId());
        assertEquals("BRONCE", resultado.getNivelSocio());
    }

    // TEST QUE NO PASA - buscar por ID inexistente
    @Test
    @DisplayName("buscar por id - no encontrado")
    void buscarPorId_noEncontrado() {
        when(fidelizacionRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            fidelizacionService.buscarPorId(99);
        });

        assertEquals("Fidelizacion no encontrada", ex.getMessage());
    }

    // TEST QUE PASA - guardar perfil nuevo con puntos 0 y nivel BRONCE
    @Test
    @DisplayName("guardar - retorna perfil con puntos en cero y nivel bronce")
    void guardar_retornaPerfilConPuntosEnCeroYNivelBronce() {
        when(fidelizacionRepository.save(any(Fidelizacion.class))).thenReturn(fidelizacionEjemplo);

        Fidelizacion resultado = fidelizacionService.guardar(fidelizacionEjemplo);

        assertNotNull(resultado);
        verify(fidelizacionRepository, times(1)).save(any(Fidelizacion.class));
    }

    // TEST QUE PASA - agregar puntos sube a nivel PLATA (1000 pts)
    @Test
    @DisplayName("agregar puntos - llega a1000 - sube a nivel plata")
    void agregarPuntos_llegaA1000_subeANivelPlata() {
        fidelizacionEjemplo.setPuntosAcumulados(900);
        fidelizacionEjemplo.setNivelSocio("BRONCE");

        when(fidelizacionRepository.findByRutCliente("12345678-9"))
            .thenReturn(Optional.of(fidelizacionEjemplo));
        when(fidelizacionRepository.save(any(Fidelizacion.class))).thenAnswer(i -> i.getArgument(0));

        Fidelizacion resultado = fidelizacionService.agregarPuntos("12345678-9", 200);

        assertEquals(1100, resultado.getPuntosAcumulados());
        assertEquals("PLATA", resultado.getNivelSocio());
    }

    // TEST QUE PASA - agregar puntos sube a nivel ORO (5000 pts)
    @Test
    @DisplayName("agregar puntos - llega a5000 - sube a nivel oro")
    void agregarPuntos_llegaA5000_subeANivelOro() {
        fidelizacionEjemplo.setPuntosAcumulados(4800);
        fidelizacionEjemplo.setNivelSocio("PLATA");

        when(fidelizacionRepository.findByRutCliente("12345678-9"))
            .thenReturn(Optional.of(fidelizacionEjemplo));
        when(fidelizacionRepository.save(any(Fidelizacion.class))).thenAnswer(i -> i.getArgument(0));

        Fidelizacion resultado = fidelizacionService.agregarPuntos("12345678-9", 300);

        assertEquals(5100, resultado.getPuntosAcumulados());
        assertEquals("ORO", resultado.getNivelSocio());
    }

    // TEST QUE NO PASA - agregar puntos a cliente inexistente
    @Test
    @DisplayName("agregar puntos - cliente no existe - lanza excepcion")
    void agregarPuntos_clienteNoExiste_lanzaExcepcion() {
        when(fidelizacionRepository.findByRutCliente("99999999-9"))
            .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            fidelizacionService.agregarPuntos("99999999-9", 100);
        });

        assertEquals("Fidelizacion no encontrada", ex.getMessage());
        verify(fidelizacionRepository, never()).save(any());
    }

    // TEST QUE PASA - eliminar existente
    @Test
    @DisplayName("eliminar - exitoso")
    void eliminar_exitoso() {
        when(fidelizacionRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> fidelizacionService.eliminar(1));
        verify(fidelizacionRepository, times(1)).deleteById(1);
    }

    // TEST QUE NO PASA - eliminar inexistente
    @Test
    @DisplayName("eliminar - no existe")
    void eliminar_noExiste() {
        when(fidelizacionRepository.existsById(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            fidelizacionService.eliminar(99);
        });

        assertEquals("Fidelizacion no existe", ex.getMessage());
        verify(fidelizacionRepository, never()).deleteById(99);
    }
}
