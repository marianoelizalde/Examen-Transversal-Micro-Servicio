package com.Automotriz.sucursalMS.service;

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

import com.Automotriz.sucursalMS.model.Sucursal;
import com.Automotriz.sucursalMS.repository.SucursalRepository;

@ExtendWith(MockitoExtension.class)
public class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private SucursalService sucursalService;

    private Sucursal sucursalEjemplo;

    @BeforeEach
    void setUp() {
        sucursalEjemplo = new Sucursal();
        sucursalEjemplo.setId(1);
        sucursalEjemplo.setNombre("Sucursal Central");
        sucursalEjemplo.setDireccion("Av. Principal 123");
        sucursalEjemplo.setComuna("Santiago");
        sucursalEjemplo.setCantidadEmpleados(10);
    }

    @Test
    @DisplayName("listar - retorna lista con sucursales")
    void listar_retornaListaConSucursales() {
        List<Sucursal> listaFalsa = new ArrayList<>();
        listaFalsa.add(sucursalEjemplo);
        when(sucursalRepository.findAll()).thenReturn(listaFalsa);

        List<Sucursal> resultado = sucursalService.listar();

        assertEquals(1, resultado.size());
        assertEquals("Sucursal Central", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("buscar por id - encontrado")
    void buscarPorId_encontrado() {
        when(sucursalRepository.findById(1)).thenReturn(Optional.of(sucursalEjemplo));

        Sucursal resultado = sucursalService.buscarPorId(1);

        assertEquals(1, resultado.getId());
        assertEquals("Sucursal Central", resultado.getNombre());
    }

    @Test
    @DisplayName("buscar por id - no encontrado")
    void buscarPorId_noEncontrado() {
        when(sucursalRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            sucursalService.buscarPorId(99);
        });

        assertEquals("Sucursal no encontrada", ex.getMessage());
    }

    @Test
    @DisplayName("guardar - retorna sucursal guardada")
    void guardar_retornaSucursalGuardada() {
        when(sucursalRepository.save(sucursalEjemplo)).thenReturn(sucursalEjemplo);

        Sucursal resultado = sucursalService.guardar(sucursalEjemplo);

        assertEquals("Sucursal Central", resultado.getNombre());
        verify(sucursalRepository, times(1)).save(sucursalEjemplo);
    }

    @Test
    @DisplayName("eliminar - exitoso")
    void eliminar_exitoso() {
        when(sucursalRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> sucursalService.eliminar(1));
        verify(sucursalRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("eliminar - no existe")
    void eliminar_noExiste() {
        when(sucursalRepository.existsById(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            sucursalService.eliminar(99);
        });

        assertEquals("Sucursal no existe", ex.getMessage());
        verify(sucursalRepository, never()).deleteById(99);
    }
}
