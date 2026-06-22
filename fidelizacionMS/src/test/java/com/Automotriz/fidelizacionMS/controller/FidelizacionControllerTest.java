package com.Automotriz.fidelizacionMS.controller;

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

import com.Automotriz.fidelizacionMS.client.UsuarioClient;
import com.Automotriz.fidelizacionMS.model.Fidelizacion;
import com.Automotriz.fidelizacionMS.service.FidelizacionService;

@WebMvcTest(FidelizacionController.class)
public class FidelizacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FidelizacionService service;

    @MockBean
    private UsuarioClient usuarioClient;

    private Fidelizacion fidelizacionEjemplo;

    @BeforeEach
    void setUp() {
        fidelizacionEjemplo = new Fidelizacion();
        fidelizacionEjemplo.setId(1);
        fidelizacionEjemplo.setRutCliente("12345678-9");
        fidelizacionEjemplo.setPuntosAcumulados(100);
        fidelizacionEjemplo.setNivelSocio("BRONCE");
    }

    // TEST QUE PASA - listar con perfiles retorna 200
    @Test
    @DisplayName("listar - retorna200 con perfiles")
    void listar_retorna200ConPerfiles() throws Exception {
        List<Fidelizacion> lista = new ArrayList<>();
        lista.add(fidelizacionEjemplo);
        when(service.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/fidelizacion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nivelSocio").value("BRONCE"));
    }

    // TEST QUE NO PASA - listar vacio retorna 204
    @Test
    @DisplayName("listar - retorna204 sin perfiles")
    void listar_retorna204SinPerfiles() throws Exception {
        when(service.listar()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/fidelizacion"))
                .andExpect(status().isNoContent());
    }

    // TEST QUE PASA - buscar por ID existente retorna 200
    @Test
    @DisplayName("buscar - retorna200")
    void buscar_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(fidelizacionEjemplo);

        mockMvc.perform(get("/api/v1/fidelizacion/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // TEST QUE NO PASA - buscar por ID inexistente retorna 404
    @Test
    @DisplayName("buscar - retorna404")
    void buscar_retorna404() throws Exception {
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Fidelizacion no encontrada"));

        mockMvc.perform(get("/api/v1/fidelizacion/99"))
                .andExpect(status().isNotFound());
    }

    // TEST QUE PASA - guardar perfil retorna 200
    @Test
    @DisplayName("guardar - retorna200")
    void guardar_retorna200() throws Exception {
        when(service.guardar(any(Fidelizacion.class))).thenReturn(fidelizacionEjemplo);

        mockMvc.perform(post("/api/v1/fidelizacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"rutCliente\":\"12345678-9\",\"puntosAcumulados\":0,\"nivelSocio\":\"BRONCE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // TEST QUE PASA - eliminar existente retorna 204
    @Test
    @DisplayName("eliminar - retorna204")
    void eliminar_retorna204() throws Exception {
        doNothing().when(service).eliminar(1);

        mockMvc.perform(delete("/api/v1/fidelizacion/1"))
                .andExpect(status().isNoContent());
    }

    // TEST QUE NO PASA - eliminar inexistente retorna 404
    @Test
    @DisplayName("eliminar - retorna404")
    void eliminar_retorna404() throws Exception {
        doThrow(new RuntimeException("Fidelizacion no existe")).when(service).eliminar(99);

        mockMvc.perform(delete("/api/v1/fidelizacion/99"))
                .andExpect(status().isNotFound());
    }
}
