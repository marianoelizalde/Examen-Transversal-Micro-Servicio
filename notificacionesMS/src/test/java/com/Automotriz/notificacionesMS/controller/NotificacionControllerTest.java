package com.Automotriz.notificacionesMS.controller;

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

import com.Automotriz.notificacionesMS.client.ReservaClient;
import com.Automotriz.notificacionesMS.model.Notificacion;
import com.Automotriz.notificacionesMS.service.NotificacionService;

@WebMvcTest(NotificacionController.class)
public class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificacionService service;

    @MockBean
    private ReservaClient reservaClient;

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

    // TEST QUE PASA - listar con notificaciones retorna 200
    @Test
    @DisplayName("listar - retorna200 con notificaciones")
    void listar_retorna200ConNotificaciones() throws Exception {
        List<Notificacion> lista = new ArrayList<>();
        lista.add(notificacionEjemplo);
        when(service.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/notificaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("PENDIENTE"));
    }

    // TEST QUE NO PASA - listar vacio retorna 204
    @Test
    @DisplayName("listar - retorna204 sin notificaciones")
    void listar_retorna204SinNotificaciones() throws Exception {
        when(service.listar()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/notificaciones"))
                .andExpect(status().isNoContent());
    }

    // TEST QUE PASA - buscar por ID existente retorna 200
    @Test
    @DisplayName("buscar - retorna200")
    void buscar_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(notificacionEjemplo);

        mockMvc.perform(get("/api/v1/notificaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // TEST QUE NO PASA - buscar por ID inexistente retorna 404
    @Test
    @DisplayName("buscar - retorna404")
    void buscar_retorna404() throws Exception {
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Notificacion no encontrada"));

        mockMvc.perform(get("/api/v1/notificaciones/99"))
                .andExpect(status().isNotFound());
    }

    // TEST QUE PASA - buscar por reserva retorna 200
    @Test
    @DisplayName("porReserva - retorna200")
    void porReserva_retorna200() throws Exception {
        List<Notificacion> lista = new ArrayList<>();
        lista.add(notificacionEjemplo);
        when(service.buscarPorReserva(1)).thenReturn(lista);

        mockMvc.perform(get("/api/v1/notificaciones/reserva/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].reservaId").value(1));
    }

    // test que pasara 
    @Test
    @DisplayName("porEstado - retorna200")
    void porEstado_retorna200() throws Exception {
        List<Notificacion> lista = new ArrayList<>();
        lista.add(notificacionEjemplo);
        when(service.buscarPorEstado("PENDIENTE")).thenReturn(lista);

        mockMvc.perform(get("/api/v1/notificaciones/estado/PENDIENTE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("PENDIENTE"));
    }

    // TEST QUE PASA - buscar por tipo retorna 200
    @Test
    @DisplayName("porTipo - retorna200")
    void porTipo_retorna200() throws Exception {
        List<Notificacion> lista = new ArrayList<>();
        lista.add(notificacionEjemplo);
        when(service.buscarPorTipo("EMAIL")).thenReturn(lista);

        mockMvc.perform(get("/api/v1/notificaciones/tipo/EMAIL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipo").value("EMAIL"));
    }

    // TEST QUE PASA - guardar notificacion retorna 200
    @Test
    @DisplayName("guardar - retorna200")
    void guardar_retorna200() throws Exception {
        when(service.guardar(any(Notificacion.class))).thenReturn(notificacionEjemplo);

        mockMvc.perform(post("/api/v1/notificaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"tipo\":\"EMAIL\",\"reservaId\":1,\"mensaje\":\"Su reserva ha sido confirmada\",\"fechaNotificacion\":\"2024-01-01\",\"estado\":\"PENDIENTE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // test que pasara
    @Test
    @DisplayName("eliminar - retorna204")
    void eliminar_retorna204() throws Exception {
        doNothing().when(service).eliminar(1);

        mockMvc.perform(delete("/api/v1/notificaciones/1"))
                .andExpect(status().isNoContent());
    }

    // test que ni pasara
    @Test
    @DisplayName("eliminar - retorna404")
    void eliminar_retorna404() throws Exception {
        doThrow(new RuntimeException("Notificacion no existe")).when(service).eliminar(99);

        mockMvc.perform(delete("/api/v1/notificaciones/99"))
                .andExpect(status().isNotFound());
    }
}
