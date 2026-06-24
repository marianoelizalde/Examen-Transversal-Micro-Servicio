package com.Automotriz.reservasMS.service;

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

import com.Automotriz.reservasMS.client.VehiculoClient;
import com.Automotriz.reservasMS.client.UsuarioClient;
import com.Automotriz.reservasMS.client.SucursalClient;
import com.Automotriz.reservasMS.dto.*;
import com.Automotriz.reservasMS.model.Reserva;
import com.Automotriz.reservasMS.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private VehiculoClient vehiculoClient;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private SucursalClient sucursalClient;

    @InjectMocks
    private ReservaService reservaService;

    private Reserva reservaEjemplo;
    private VehiculoDTO vehiculoDisponible;
    private VehiculoDTO vehiculoNoDisponible;
    private UsuarioDTO usuarioEjemplo;
    private ClienteDTO clienteEjemplo;
    private AntecedentesDTO antecedentesOk;
    private AntecedentesDTO antecedentesConMultas;
    private SucursalDTO sucursalEjemplo;

    @BeforeEach
    void setUp() {
        reservaEjemplo = new Reserva();
        reservaEjemplo.setId(1);
        reservaEjemplo.setRutCliente("12345678-9");
        reservaEjemplo.setPatente("ABCD12");
        reservaEjemplo.setSucursalId(1);
        reservaEjemplo.setFechaInicio("2024-01-01");
        reservaEjemplo.setFechaFin("2024-01-10");
        reservaEjemplo.setEstado("PENDIENTE");

        vehiculoDisponible = new VehiculoDTO(1, "Corolla", "Sedan", "Toyota", "ABCD12", "DISPONIBLE");
        vehiculoNoDisponible = new VehiculoDTO(1, "Corolla", "Sedan", "Toyota", "ABCD12", "ARRENDADO");

        usuarioEjemplo = new UsuarioDTO(1, "12345678-9", "Juan Perez", "juan@gmail.com", "+56912345678", 1);
        clienteEjemplo = new ClienteDTO(1, "REGULAR");
        antecedentesOk = new AntecedentesDTO(1, "VIGENTE", 0);
        antecedentesConMultas = new AntecedentesDTO(1, "VIGENTE", 5);
        sucursalEjemplo = new SucursalDTO(1, "Sucursal Central", "Av. Principal 123", "Santiago");
    }

    // TEST QUE PASA - listar retorna lista con reservas
    @Test
    @DisplayName("listar - retorna lista con reservas")
    void listar_retornaListaConReservas() {
        List<Reserva> listaFalsa = new ArrayList<>();
        listaFalsa.add(reservaEjemplo);
        when(reservaRepository.findAll()).thenReturn(listaFalsa);

        List<Reserva> resultado = reservaService.listar();

        assertEquals(1, resultado.size());
        assertEquals("12345678-9", resultado.get(0).getRutCliente());
    }

    // TEST QUE NO PASA - listar retorna lista vacia
    @Test
    @DisplayName("listar - retorna lista vacia")
    void listar_retornaListaVacia() {
        when(reservaRepository.findAll()).thenReturn(new ArrayList<>());

        List<Reserva> resultado = reservaService.listar();

        assertEquals(0, resultado.size());
    }

    // TEST QUE PASA - buscar por ID existente
    @Test
    @DisplayName("buscar por id - encontrado")
    void buscarPorId_encontrado() {
        when(reservaRepository.findById(1)).thenReturn(Optional.of(reservaEjemplo));

        Reserva resultado = reservaService.buscarPorId(1);

        assertEquals(1, resultado.getId());
        assertEquals("PENDIENTE", resultado.getEstado());
    }

    // TEST QUE NO PASA - buscar por ID inexistente lanza excepcion
    @Test
    @DisplayName("buscar por id - no encontrado")
    void buscarPorId_noEncontrado() {
        when(reservaRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reservaService.buscarPorId(99);
        });

        assertEquals("Reserva no encontrada", ex.getMessage());
    }

    // TEST QUE PASA - guardar con vehiculo disponible y antecedentes ok
    @Test
    @DisplayName("guardar - vehiculo disponible y antecedentes ok - retorna reserva")
    void guardar_vehiculoDisponibleYAntecedentesOk_retornaReserva() {
        when(vehiculoClient.obtenerVehiculoPorPatente("ABCD12")).thenReturn(vehiculoDisponible);
        when(reservaRepository.existsByPatenteAndEstado("ABCD12", "ACTIVA")).thenReturn(false);
        when(usuarioClient.obtenerUsuarioPorRut("12345678-9")).thenReturn(usuarioEjemplo);
        when(usuarioClient.obtenerClientePorUsuarioId(1)).thenReturn(clienteEjemplo);
        when(usuarioClient.obtenerAntecedentesPorClienteId(1)).thenReturn(antecedentesOk);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaEjemplo);

        Reserva resultado = reservaService.guardar(reservaEjemplo);

        assertNotNull(resultado);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    // TEST QUE NO PASA - guardar con vehiculo NO disponible lanza excepcion
    @Test
    @DisplayName("guardar - vehiculo no disponible - lanza excepcion")
    void guardar_vehiculoNoDisponible_lanzaExcepcion() {
        when(vehiculoClient.obtenerVehiculoPorPatente("ABCD12")).thenReturn(vehiculoNoDisponible);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reservaService.guardar(reservaEjemplo);
        });

        assertTrue(ex.getMessage().contains("no está disponible"));
        verify(reservaRepository, never()).save(any());
    }

    // TEST QUE NO PASA - guardar con fecha fin anterior a fecha inicio
    @Test
    @DisplayName("guardar - fecha fin anterior a inicio - lanza excepcion")
    void guardar_fechaFinAnteriorAInicio_lanzaExcepcion() {
        reservaEjemplo.setFechaInicio("2024-01-10");
        reservaEjemplo.setFechaFin("2024-01-01");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reservaService.guardar(reservaEjemplo);
        });

        assertEquals("La fecha de fin debe ser posterior a la fecha de inicio", ex.getMessage());
        verify(vehiculoClient, never()).obtenerVehiculoPorPatente(any());
    }

    // TEST QUE NO PASA - guardar cuando cliente tiene muchas multas
    @Test
    @DisplayName("guardar - cliente con muchas multas - lanza excepcion")
    void guardar_clienteConMuchasMultas_lanzaExcepcion() {
        when(vehiculoClient.obtenerVehiculoPorPatente("ABCD12")).thenReturn(vehiculoDisponible);
        when(reservaRepository.existsByPatenteAndEstado("ABCD12", "ACTIVA")).thenReturn(false);
        when(usuarioClient.obtenerUsuarioPorRut("12345678-9")).thenReturn(usuarioEjemplo);
        when(usuarioClient.obtenerClientePorUsuarioId(1)).thenReturn(clienteEjemplo);
        when(usuarioClient.obtenerAntecedentesPorClienteId(1)).thenReturn(antecedentesConMultas);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reservaService.guardar(reservaEjemplo);
        });

        assertTrue(ex.getMessage().contains("multas"));
        verify(reservaRepository, never()).save(any());
    }

    // TEST QUE PASA - obtener detalle con Feign mockeado
    @Test
    @DisplayName("obtener detalle - retorna detalle")
    void obtenerDetalle_retornaDetalle() {
        when(reservaRepository.findById(1)).thenReturn(Optional.of(reservaEjemplo));
        when(usuarioClient.obtenerUsuarioPorRut("12345678-9")).thenReturn(usuarioEjemplo);
        when(vehiculoClient.obtenerVehiculoPorPatente("ABCD12")).thenReturn(vehiculoDisponible);
        when(sucursalClient.obtenerSucursal(1)).thenReturn(sucursalEjemplo);

        ReservaDetalleDTO detalle = reservaService.obtenerDetalle(1);

        assertNotNull(detalle);
        assertEquals(1, detalle.getId());
        assertEquals("PENDIENTE", detalle.getEstado());
    }

    // TEST QUE NO PASA - obtener detalle con reserva inexistente
    @Test
    @DisplayName("obtener detalle - reserva no existe - lanza excepcion")
    void obtenerDetalle_reservaNoExiste_lanzaExcepcion() {
        when(reservaRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reservaService.obtenerDetalle(99);
        });

        assertEquals("Reserva no encontrada", ex.getMessage());
    }

    // TEST QUE PASA - eliminar existente
    @Test
    @DisplayName("eliminar - exitoso")
    void eliminar_exitoso() {
        when(reservaRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> reservaService.eliminar(1));
        verify(reservaRepository, times(1)).deleteById(1);
    }

    // TEST QUE NO PASA - eliminar inexistente
    @Test
    @DisplayName("eliminar - no existe")
    void eliminar_noExiste() {
        when(reservaRepository.existsById(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reservaService.eliminar(99);
        });

        assertEquals("Reserva no existe", ex.getMessage());
        verify(reservaRepository, never()).deleteById(99);
    }
}
