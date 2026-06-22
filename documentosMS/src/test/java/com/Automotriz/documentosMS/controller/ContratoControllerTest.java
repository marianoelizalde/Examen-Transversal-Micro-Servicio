package com.Automotriz.documentosMS.controller;

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

import com.Automotriz.documentosMS.client.ReservaClient;
import com.Automotriz.documentosMS.model.Contrato;
import com.Automotriz.documentosMS.service.ContratoService;

@WebMvcTest(ContratoController.class)
public class ContratoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContratoService service;

    @MockBean
    private ReservaClient reservaClient;

    private Contrato contratoEjemplo;

    @BeforeEach
    void setUp() {
        contratoEjemplo = new Contrato();
        contratoEjemplo.setId(1);
        contratoEjemplo.setReservaId(1);
        contratoEjemplo.setFechaEmision("2024-01-01");
        contratoEjemplo.setEstado("PENDIENTE");
        contratoEjemplo.setClausulas("Clausulas del contrato de arrendamiento vehicular");
    }

    // TEST QUE PASA - listar con contratos retorna 200
    @Test
    @DisplayName("listar - retorna200 con contratos")
    void listar_retorna200ConContratos() throws Exception {
        List<Contrato> lista = new ArrayList<>();
        lista.add(contratoEjemplo);
        when(service.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/contratos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("PENDIENTE"));
    }

    // TEST QUE NO PASA - listar vacio retorna 204
    @Test
    @DisplayName("listar - retorna204 sin contratos")
    void listar_retorna204SinContratos() throws Exception {
        when(service.listar()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/contratos"))
                .andExpect(status().isNoContent());
    }

    // TEST QUE PASA - buscar por ID existente retorna 200
    @Test
    @DisplayName("buscar - retorna200")
    void buscar_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(contratoEjemplo);

        mockMvc.perform(get("/api/v1/contratos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // TEST QUE NO PASA - buscar por ID inexistente retorna 404
    @Test
    @DisplayName("buscar - retorna404")
    void buscar_retorna404() throws Exception {
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Contrato no encontrado"));

        mockMvc.perform(get("/api/v1/contratos/99"))
                .andExpect(status().isNotFound());
    }

    // TEST QUE PASA - guardar contrato retorna 200
    @Test
    @DisplayName("guardar - retorna200")
    void guardar_retorna200() throws Exception {
        when(service.guardar(any(Contrato.class))).thenReturn(contratoEjemplo);

        mockMvc.perform(post("/api/v1/contratos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"reservaId\":1,\"fechaEmision\":\"2024-01-01\",\"estado\":\"PENDIENTE\",\"clausulas\":\"Clausulas del contrato\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // TEST QUE PASA - eliminar existente retorna 204
    @Test
    @DisplayName("eliminar - retorna204")
    void eliminar_retorna204() throws Exception {
        doNothing().when(service).eliminar(1);

        mockMvc.perform(delete("/api/v1/contratos/1"))
                .andExpect(status().isNoContent());
    }

    // TEST QUE NO PASA - eliminar inexistente retorna 404
    @Test
    @DisplayName("eliminar - retorna404")
    void eliminar_retorna404() throws Exception {
        doThrow(new RuntimeException("Contrato no existe")).when(service).eliminar(99);

        mockMvc.perform(delete("/api/v1/contratos/99"))
                .andExpect(status().isNotFound());
    }
}
