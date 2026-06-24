package com.Automotriz.vehiculoMS.controller;

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

import com.Automotriz.vehiculoMS.model.Vehiculo;
import com.Automotriz.vehiculoMS.service.VehiculoService;

@WebMvcTest(VehiculoController.class)
public class VehiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehiculoService service;

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
    @DisplayName("listar - retorna200 con vehiculos")
    void listar_retorna200ConVehiculos() throws Exception {
        List<Vehiculo> listaFalsa = new ArrayList<>();
        listaFalsa.add(vehiculoEjemplo);
        when(service.listar()).thenReturn(listaFalsa);

        mockMvc.perform(get("/api/v1/vehiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Corolla"));
    }

    @Test
    @DisplayName("listar - retorna204 sin vehiculos")
    void listar_retorna204SinVehiculos() throws Exception {
        when(service.listar()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/vehiculos"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("buscar por id - retorna200")
    void buscarPorId_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(vehiculoEjemplo);

        mockMvc.perform(get("/api/v1/vehiculos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Corolla"));
    }

    @Test
    @DisplayName("buscar por id - retorna404")
    void buscarPorId_retorna404() throws Exception {
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Vehículo no encontrado"));

        mockMvc.perform(get("/api/v1/vehiculos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("guardar - retorna200")
    void guardar_retorna200() throws Exception {
        when(service.guardar(any(Vehiculo.class))).thenReturn(vehiculoEjemplo);

        mockMvc.perform(post("/api/v1/vehiculos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Corolla\",\"modelo\":\"Sedan\",\"marca\":\"Toyota\",\"anio\":2022,\"estado\":\"DISPONIBLE\",\"patente\":\"ABCD12\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Corolla"));
    }

    @Test
    @DisplayName("eliminar - retorna204")
    void eliminar_retorna204() throws Exception {
        doNothing().when(service).eliminar(1);

        mockMvc.perform(delete("/api/v1/vehiculos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("eliminar - retorna404")
    void eliminar_retorna404() throws Exception {
        doThrow(new RuntimeException("Vehículo no existe")).when(service).eliminar(99);

        mockMvc.perform(delete("/api/v1/vehiculos/99"))
                .andExpect(status().isNotFound());
    }
}
