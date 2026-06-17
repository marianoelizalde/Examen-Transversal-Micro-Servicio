package com.Automotriz.vehiculoMS.service;

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

import com.Automotriz.vehiculoMS.model.Vehiculo;
import com.Automotriz.vehiculoMS.repository.VehiculoRepository;

@ExtendWith(MockitoExtension.class)
public class VehiculoServiceTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @InjectMocks
    private VehiculoService vehiculoService;

    private Vehiculo vehiculoEjemplo;

    @BeforeEach
    void setUp() {
        vehiculoEjemplo = new Vehiculo();
        vehiculoEjemplo.setId(1);
        vehiculoEjemplo.setNombre("Corolla");
        vehiculoEjemplo.setModelo("Sedan");
        vehiculoEjemplo.setMarca("Toyota");
        vehiculoEjemplo.setAnio(2022);
        vehiculoEjemplo.setEstado("DISPONIBLE");
        vehiculoEjemplo.setPatente("ABCD12");
    }

    @Test
    void listar_retornaListaConVehiculos() {
        List<Vehiculo> listaFalsa = new ArrayList<>();
        listaFalsa.add(vehiculoEjemplo);
        when(vehiculoRepository.findAll()).thenReturn(listaFalsa);

        List<Vehiculo> resultado = vehiculoService.listar();

        assertEquals(1, resultado.size());
        assertEquals("Corolla", resultado.get(0).getNombre());
    }

    @Test
    void buscarPorId_encontrado() {
        when(vehiculoRepository.findById(1)).thenReturn(Optional.of(vehiculoEjemplo));

        Vehiculo resultado = vehiculoService.buscarPorId(1);

        assertEquals(1, resultado.getId());
        assertEquals("Corolla", resultado.getNombre());
    }

    @Test
    void buscarPorId_noEncontrado() {
        when(vehiculoRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            vehiculoService.buscarPorId(99);
        });

        assertEquals("Vehículo no encontrado", ex.getMessage());
    }

    @Test
    void guardar_retornaVehiculoGuardado() {
        when(vehiculoRepository.save(vehiculoEjemplo)).thenReturn(vehiculoEjemplo);

        Vehiculo resultado = vehiculoService.guardar(vehiculoEjemplo);

        assertEquals("Corolla", resultado.getNombre());
        verify(vehiculoRepository, times(1)).save(vehiculoEjemplo);
    }

    @Test
    void eliminar_exitoso() {
        when(vehiculoRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> vehiculoService.eliminar(1));
        verify(vehiculoRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noExiste() {
        when(vehiculoRepository.existsById(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            vehiculoService.eliminar(99);
        });

        assertEquals("Vehículo no existe", ex.getMessage());
        verify(vehiculoRepository, never()).deleteById(99);
    }
}
