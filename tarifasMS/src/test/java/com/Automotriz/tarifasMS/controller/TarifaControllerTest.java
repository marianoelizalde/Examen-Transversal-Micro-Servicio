package com.Automotriz.tarifasMS.controller;

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

import com.Automotriz.tarifasMS.client.VehiculoClient;
import com.Automotriz.tarifasMS.model.Tarifa;
import com.Automotriz.tarifasMS.service.TarifaService;

@WebMvcTest(TarifaController.class)
public class TarifaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TarifaService service;

    @MockBean
    private VehiculoClient vehiculoClient;

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

    // TEST QUE PASA - listar con tarifas retorna 200
    @Test
    @DisplayName("listar - retorna200 con tarifas")
    void listar_retorna200ConTarifas() throws Exception {
        List<Tarifa> lista = new ArrayList<>();
        lista.add(tarifaEjemplo);
        when(service.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/tarifas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("ACTIVO"));
    }

    // TEST QUE NO PASA - listar vacio retorna 204
    @Test
    @DisplayName("listar - retorna204 sin tarifas")
    void listar_retorna204SinTarifas() throws Exception {
        when(service.listar()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/tarifas"))
                .andExpect(status().isNoContent());
    }

    // TEST QUE PASA - buscar por ID existente retorna 200
    @Test
    @DisplayName("buscar - retorna200")
    void buscar_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(tarifaEjemplo);

        mockMvc.perform(get("/api/v1/tarifas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // TEST QUE NO PASA - buscar por ID inexistente retorna 404
    @Test
    @DisplayName("buscar - retorna404")
    void buscar_retorna404() throws Exception {
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Tarifa no encontrada"));

        mockMvc.perform(get("/api/v1/tarifas/99"))
                .andExpect(status().isNotFound());
    }

    // TEST QUE PASA - guardar tarifa retorna 200
    @Test
    @DisplayName("guardar - retorna200")
    void guardar_retorna200() throws Exception {
        when(service.guardar(any(Tarifa.class))).thenReturn(tarifaEjemplo);

        mockMvc.perform(post("/api/v1/tarifas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"vehiculoId\":1,\"precioDia\":50000.0,\"temporada\":\"NORMAL\",\"estado\":\"ACTIVO\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // TEST QUE PASA - eliminar existente retorna 204
    @Test
    @DisplayName("eliminar - retorna204")
    void eliminar_retorna204() throws Exception {
        doNothing().when(service).eliminar(1);

        mockMvc.perform(delete("/api/v1/tarifas/1"))
                .andExpect(status().isNoContent());
    }

    // TEST QUE NO PASA - eliminar inexistente retorna 404
    @Test
    @DisplayName("eliminar - retorna404")
    void eliminar_retorna404() throws Exception {
        doThrow(new RuntimeException("Tarifa no existe")).when(service).eliminar(99);

        mockMvc.perform(delete("/api/v1/tarifas/99"))
                .andExpect(status().isNotFound());
    }
}
