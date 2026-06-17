package com.Automotriz.usuarioMS.controller;

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

import com.Automotriz.usuarioMS.model.Usuario;
import com.Automotriz.usuarioMS.service.UsuarioService;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService service;

    private Usuario usuarioEjemplo;

    @BeforeEach
    void setUp() {
        usuarioEjemplo = new Usuario();
        usuarioEjemplo.setId(1);
        usuarioEjemplo.setRut("12345678-9");
        usuarioEjemplo.setNombre("Juan Pérez");
        usuarioEjemplo.setCorreo("juan@gmail.com");
        usuarioEjemplo.setTelefono("+56912345678");
        usuarioEjemplo.setDireccion("Calle 123");
        usuarioEjemplo.setTipo(1);
        usuarioEjemplo.setClaveUnica("clave123");
    }

    @Test
    void listar_retorna200ConUsuarios() throws Exception {
        List<Usuario> listaFalsa = new ArrayList<>();
        listaFalsa.add(usuarioEjemplo);
        when(service.listar()).thenReturn(listaFalsa);

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan Pérez"));
    }

    @Test
    void listar_retorna204SinUsuarios() throws Exception {
        when(service.listar()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(usuarioEjemplo);

        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"));
    }

    @Test
    void buscarPorId_retorna404() throws Exception {
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Usuario no encontrado"));

        mockMvc.perform(get("/api/v1/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void guardar_retorna200() throws Exception {
        when(service.guardar(any(Usuario.class))).thenReturn(usuarioEjemplo);

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"rut\":\"12345678-9\",\"nombre\":\"Juan Pérez\",\"correo\":\"juan@gmail.com\",\"telefono\":\"+56912345678\",\"direccion\":\"Calle 123\",\"tipo\":1,\"claveUnica\":\"clave123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"));
    }

    @Test
    void eliminar_retorna204() throws Exception {
        doNothing().when(service).eliminar(1);

        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_retorna404() throws Exception {
        doThrow(new RuntimeException("Usuario no existe")).when(service).eliminar(99);

        mockMvc.perform(delete("/api/v1/usuarios/99"))
                .andExpect(status().isNotFound());
    }
}
