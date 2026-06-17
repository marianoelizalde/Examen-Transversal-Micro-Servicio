package com.Automotriz.sucursalMS.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.Automotriz.sucursalMS.model.Sucursal;
import com.Automotriz.sucursalMS.service.SucursalService;

@WebMvcTest(SucursalController.class)
public class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SucursalService service;

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
    void listar_retorna200ConSucursales() throws Exception {
        List<Sucursal> listaFalsa = new ArrayList<>();
        listaFalsa.add(sucursalEjemplo);
        when(service.listar()).thenReturn(listaFalsa);

        mockMvc.perform(get("/api/v1/sucursales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Sucursal Central"));
    }

    @Test
    void listar_retorna204SinSucursales() throws Exception {
        when(service.listar()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/sucursales"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(sucursalEjemplo);

        mockMvc.perform(get("/api/v1/sucursales/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Sucursal Central"));
    }

    @Test
    void buscarPorId_retorna404() throws Exception {
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Sucursal no encontrada"));

        mockMvc.perform(get("/api/v1/sucursales/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void guardar_retorna200() throws Exception {
        when(service.guardar(any(Sucursal.class))).thenReturn(sucursalEjemplo);

        mockMvc.perform(post("/api/v1/sucursales")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"nombre":"Sucursal Central","direccion":"Av. Principal 123","comuna":"Santiago","cantidadEmpleados":10}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Sucursal Central"));
    }

    @Test
    void eliminar_retorna204() throws Exception {
        doNothing().when(service).eliminar(1);

        mockMvc.perform(delete("/api/v1/sucursales/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_retorna404() throws Exception {
        doThrow(new RuntimeException("Sucursal no existe")).when(service).eliminar(99);

        mockMvc.perform(delete("/api/v1/sucursales/99"))
                .andExpect(status().isNotFound());
    }
}
