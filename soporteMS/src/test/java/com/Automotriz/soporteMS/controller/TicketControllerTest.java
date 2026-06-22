package com.Automotriz.soporteMS.controller;

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

import com.Automotriz.soporteMS.client.ReservaClient;
import com.Automotriz.soporteMS.model.Ticket;
import com.Automotriz.soporteMS.service.TicketService;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService service;

    @MockBean
    private ReservaClient reservaClient;

    private Ticket ticketEjemplo;

    @BeforeEach
    void setUp() {
        ticketEjemplo = new Ticket();
        ticketEjemplo.setId(1);
        ticketEjemplo.setReservaId(1);
        ticketEjemplo.setAsunto("Problema con la reserva");
        ticketEjemplo.setDescripcion("Descripcion detallada del problema");
        ticketEjemplo.setEstado("ABIERTO");
        ticketEjemplo.setFechaCreacion("2024-01-01");
    }

    // TEST QUE PASA - listar con tickets retorna 200
    @Test
    @DisplayName("listar - retorna200 con tickets")
    void listar_retorna200ConTickets() throws Exception {
        List<Ticket> lista = new ArrayList<>();
        lista.add(ticketEjemplo);
        when(service.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("ABIERTO"));
    }

    // TEST QUE NO PASA - listar vacio retorna 204
    @Test
    @DisplayName("listar - retorna204 sin tickets")
    void listar_retorna204SinTickets() throws Exception {
        when(service.listar()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/tickets"))
                .andExpect(status().isNoContent());
    }

    // TEST QUE PASA - buscar por ID existente retorna 200
    @Test
    @DisplayName("buscar - retorna200")
    void buscar_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(ticketEjemplo);

        mockMvc.perform(get("/api/v1/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // TEST QUE NO PASA - buscar por ID inexistente retorna 404
    @Test
    @DisplayName("buscar - retorna404")
    void buscar_retorna404() throws Exception {
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Ticket no encontrado"));

        mockMvc.perform(get("/api/v1/tickets/99"))
                .andExpect(status().isNotFound());
    }

    // TEST QUE PASA - guardar ticket retorna 200
    @Test
    @DisplayName("guardar - retorna200")
    void guardar_retorna200() throws Exception {
        when(service.guardar(any(Ticket.class))).thenReturn(ticketEjemplo);

        mockMvc.perform(post("/api/v1/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"reservaId\":1,\"asunto\":\"Problema con la reserva\",\"descripcion\":\"Descripcion del problema\",\"estado\":\"ABIERTO\",\"fechaCreacion\":\"2024-01-01\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // TEST QUE PASA - eliminar existente retorna 204
    @Test
    @DisplayName("eliminar - retorna204")
    void eliminar_retorna204() throws Exception {
        doNothing().when(service).eliminar(1);

        mockMvc.perform(delete("/api/v1/tickets/1"))
                .andExpect(status().isNoContent());
    }

    // TEST QUE NO PASA - eliminar inexistente retorna 404
    @Test
    @DisplayName("eliminar - retorna404")
    void eliminar_retorna404() throws Exception {
        doThrow(new RuntimeException("Ticket no existe")).when(service).eliminar(99);

        mockMvc.perform(delete("/api/v1/tickets/99"))
                .andExpect(status().isNotFound());
    }
}
