package com.Automotriz.usuarioMS.service;

import com.Automotriz.usuarioMS.model.Usuario;
import com.Automotriz.usuarioMS.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class UsuarioService {

    @Autowired private UsuarioRepository usuarioRepository;

    public List<Usuario> listar() {
        log.info("Listando todos los usuarios");
        return usuarioRepository.findAll();
    }

    public List<Usuario> listarClientes() {
        log.info("Listando usuarios tipo CLIENTE");
        return usuarioRepository.findByTipo(1);
    }

    public List<Usuario> listarEmpleados() {
        log.info("Listando usuarios tipo EMPLEADO");
        return usuarioRepository.findByTipo(2);
    }

    public Usuario buscarPorId(Integer id) {
        log.info("Buscando usuario con ID: {}", id);
        return usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Usuario con ID {} no encontrado", id);
                    return new RuntimeException("Usuario no encontrado");
                });
    }

    public Usuario buscarPorRut(String rut) {
        log.info("Buscando usuario con RUT: {}", rut);
        return usuarioRepository.findByRut(rut)
                .orElseThrow(() -> {
                    log.error("Usuario con RUT {} no encontrado", rut);
                    return new RuntimeException("Usuario no encontrado");
                });
    }

    public Usuario guardar(Usuario usuario) {
        Usuario resultado = usuarioRepository.save(usuario);
        log.info("Usuario creado con ID: {} RUT: {}", resultado.getId(), resultado.getRut());
        return resultado;
    }

    public Usuario actualizar(Integer id, Usuario datos) {
        log.info("Actualizando usuario con ID: {}", id);
        Usuario usuario = buscarPorId(id);
        usuario.setRut(datos.getRut());
        usuario.setNombre(datos.getNombre());
        usuario.setCorreo(datos.getCorreo());
        usuario.setTelefono(datos.getTelefono());
        usuario.setDireccion(datos.getDireccion());
        usuario.setTipo(datos.getTipo());
        usuario.setClaveUnica(datos.getClaveUnica());
        Usuario resultado = usuarioRepository.save(usuario);
        log.info("Usuario {} actualizado correctamente", id);
        return resultado;
    }

    public void eliminar(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            log.error("No se puede eliminar. Usuario con ID {} no existe", id);
            throw new RuntimeException("Usuario no existe");
        }
        usuarioRepository.deleteById(id);
        log.info("Usuario con ID {} eliminado correctamente", id);
    }
}
