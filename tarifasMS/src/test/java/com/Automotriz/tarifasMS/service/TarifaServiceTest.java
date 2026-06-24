package com.Automotriz.tarifasMS.service;

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

import com.Automotriz.tarifasMS.model.Tarifa;
import com.Automotriz.tarifasMS.repository.TarifaRepository;

@ExtendWith(MockitoExtension.class)
public class TarifaServiceTest {

    @Mock
    private TarifaRepository tarifaRepository;

    @InjectMocks
    private TarifaService tarifaService;

    private Tarifa tarifaEjemplo;

    @BeforeEach
    void setUp() {
        tarifaEjemplo = new Tarifa();
        tarifaEjemplo.setId(1);
        tarifaEjemplo.setVehiculoId(1);
        tarifaEjemplo.setPrecioDia(50000.0);
        tarifaEjemplo.setTemporada("NORMAL");
        tarifaEjemplo.setEstado("ACTIVO");
    }

    // TEST QUE PASA - listar retorna tarifas
    @Test
    @DisplayName("listar - retorna lista con tarifas")
    void listar_retornaListaConTarifas() {
        List<Tarifa> lista = new ArrayList<>();
        lista.add(tarifaEjemplo);
        when(tarifaRepository.findAll()).thenReturn(lista);

        List<Tarifa> resultado = tarifaService.listar();

        assertEquals(1, resultado.size());
        assertEquals(50000.0, resultado.get(0).getPrecioDia());
    }

    // TEST QUE PASA - buscar por ID existente
    @Test
    @DisplayName("buscar por id - encontrado")
    void buscarPorId_encontrado() {
        when(tarifaRepository.findById(1)).thenReturn(Optional.of(tarifaEjemplo));

        Tarifa resultado = tarifaService.buscarPorId(1);

        assertEquals(1, resultado.getId());
        assertEquals("ACTIVO", resultado.getEstado());
    }

    // TEST QUE NO PASA - buscar por ID inexistente
    @Test
    @DisplayName("buscar por id - no encontrado")
    void buscarPorId_noEncontrado() {
        when(tarifaRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            tarifaService.buscarPorId(99);
        });

        assertEquals("Tarifa no encontrada", ex.getMessage());
    }

    // TEST QUE PASA - buscar por vehiculo
    @Test
    @DisplayName("buscar por vehiculo - retorna tarifas")
    void buscarPorVehiculo_retornaTarifas() {
        List<Tarifa> lista = new ArrayList<>();
        lista.add(tarifaEjemplo);
        when(tarifaRepository.findByVehiculoId(1)).thenReturn(lista);

        List<Tarifa> resultado = tarifaService.buscarPorVehiculo(1);

        assertEquals(1, resultado.size());
    }

    // TEST QUE PASA - guardar con precio valido
    @Test
    @DisplayName("guardar - precio valido - retorna tarifa")
    void guardar_precioValido_retornaTarifa() {
        when(tarifaRepository.save(any(Tarifa.class))).thenReturn(tarifaEjemplo);

        Tarifa resultado = tarifaService.guardar(tarifaEjemplo);

        assertNotNull(resultado);
        assertEquals(50000.0, resultado.getPrecioDia());
        verify(tarifaRepository, times(1)).save(any(Tarifa.class));
    }

    // TEST QUE NO PASA - guardar con precio invalido
    @Test
    @DisplayName("guardar - precio invalido - lanza excepcion")
    void guardar_precioInvalido_lanzaExcepcion() {
        tarifaEjemplo.setPrecioDia(-100.0);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            tarifaService.guardar(tarifaEjemplo);
        });

        assertEquals("El precio debe ser mayor a 0", ex.getMessage());
        verify(tarifaRepository, never()).save(any());
    }

    // TEST QUE PASA - eliminar existente
    @Test
    @DisplayName("eliminar - exitoso")
    void eliminar_exitoso() {
        when(tarifaRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> tarifaService.eliminar(1));
        verify(tarifaRepository, times(1)).deleteById(1);
    }

    // TEST QUE NO PASA - eliminar inexistente
    @Test
    @DisplayName("eliminar - no existe")
    void eliminar_noExiste() {
        when(tarifaRepository.existsById(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            tarifaService.eliminar(99);
        });

        assertEquals("Tarifa no existe", ex.getMessage());
        verify(tarifaRepository, never()).deleteById(99);
    }
}
