package com.Automotriz.usuarioMS.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Automotriz.usuarioMS.model.Usuario;
import com.Automotriz.usuarioMS.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

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
    void listar_retornaListaConUsuarios() {
        List<Usuario> listaFalsa = new ArrayList<>();
        listaFalsa.add(usuarioEjemplo);
        when(usuarioRepository.findAll()).thenReturn(listaFalsa);

        List<Usuario> resultado = usuarioService.listar();

        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());
    }

    @Test
    void buscarPorId_encontrado() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioEjemplo));

        Usuario resultado = usuarioService.buscarPorId(1);

        assertEquals(1, resultado.getId());
        assertEquals("Juan Pérez", resultado.getNombre());
    }

    @Test
    void buscarPorId_noEncontrado() {
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            usuarioService.buscarPorId(99);
        });

        assertEquals("Usuario no encontrado", ex.getMessage());
    }

    @Test
    void guardar_retornaUsuarioGuardado() {
        when(usuarioRepository.save(usuarioEjemplo)).thenReturn(usuarioEjemplo);

        Usuario resultado = usuarioService.guardar(usuarioEjemplo);

        assertEquals("Juan Pérez", resultado.getNombre());
        verify(usuarioRepository, times(1)).save(usuarioEjemplo);
    }

    @Test
    void eliminar_exitoso() {
        when(usuarioRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> usuarioService.eliminar(1));
        verify(usuarioRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noExiste() {
        when(usuarioRepository.existsById(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            usuarioService.eliminar(99);
        });

        assertEquals("Usuario no existe", ex.getMessage());
        verify(usuarioRepository, never()).deleteById(99);
    }
}
