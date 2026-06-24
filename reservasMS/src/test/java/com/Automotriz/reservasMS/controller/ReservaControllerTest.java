package com.Automotriz.reservasMS.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.Automotriz.reservasMS.client.VehiculoClient;
import com.Automotriz.reservasMS.client.UsuarioClient;
import com.Automotriz.reservasMS.client.SucursalClient;
import com.Automotriz.reservasMS.model.Reserva;
import com.Automotriz.reservasMS.service.ReservaService;

@WebMvcTest(ReservaController.class)
public class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaService service;

    @MockBean
    private VehiculoClient vehiculoClient;

    @MockBean
    private UsuarioClient usuarioClient;

    @MockBean
    private SucursalClient sucursalClient;

    private Reserva reservaEjemplo;

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
    }

    // TEST QUE PASA - listar con reservas retorna 200
    @Test
    @DisplayName("listar - retorna200 con reservas")
    void listar_retorna200ConReservas() throws Exception {
        List<Reserva> lista = new ArrayList<>();
        lista.add(reservaEjemplo);
        when(service.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("PENDIENTE"));
    }

    // TEST QUE NO PASA - listar vacio retorna 204
    @Test
    @DisplayName("listar - retorna204 sin reservas")
    void listar_retorna204SinReservas() throws Exception {
        when(service.listar()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/reservas"))
                .andExpect(status().isNoContent());
    }

    // TEST QUE PASA - buscar por ID existente retorna 200
    @Test
    @DisplayName("buscar - retorna200")
    void buscar_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(reservaEjemplo);

        mockMvc.perform(get("/api/v1/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // TEST QUE NO PASA - buscar por ID inexistente retorna 404
    @Test
    @DisplayName("buscar - retorna404")
    void buscar_retorna404() throws Exception {
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Reserva no encontrada"));

        mockMvc.perform(get("/api/v1/reservas/99"))
                .andExpect(status().isNotFound());
    }

    // TEST QUE PASA - guardar reserva valida retorna 200
    @Test
    @DisplayName("guardar - retorna200")
    void guardar_retorna200() throws Exception {
        when(service.guardar(any(Reserva.class))).thenReturn(reservaEjemplo);

        mockMvc.perform(post("/api/v1/reservas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"rutCliente\":\"12345678-9\",\"patente\":\"ABCD12\",\"sucursalId\":1,\"fechaInicio\":\"2024-01-01\",\"fechaFin\":\"2024-01-10\",\"estado\":\"PENDIENTE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // TEST QUE PASA - eliminar existente retorna 204
    @Test
    @DisplayName("eliminar - retorna204")
    void eliminar_retorna204() throws Exception {
        doNothing().when(service).eliminar(1);

        mockMvc.perform(delete("/api/v1/reservas/1"))
                .andExpect(status().isNoContent());
    }

    // TEST QUE NO PASA - eliminar inexistente retorna 404
    @Test
    @DisplayName("eliminar - retorna404")
    void eliminar_retorna404() throws Exception {
        doThrow(new RuntimeException("Reserva no existe")).when(service).eliminar(99);

        mockMvc.perform(delete("/api/v1/reservas/99"))
                .andExpect(status().isNotFound());
    }
}
