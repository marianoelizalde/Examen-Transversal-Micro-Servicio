package com.Automotriz.usuarioMS.service;

import com.Automotriz.usuarioMS.model.Cliente;
import com.Automotriz.usuarioMS.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listar() {
        log.info("Listando todos los clientes");
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Integer id) {
        log.info("Buscando cliente con ID: {}", id);
        return clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cliente con ID {} no encontrado", id);
                    return new RuntimeException("Cliente no encontrado");
                });
    }

    public Cliente buscarPorUsuarioId(Integer usuarioId) {
        log.info("Buscando cliente con usuarioId: {}", usuarioId);
        return clienteRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> {
                    log.error("Cliente con usuarioId {} no encontrado", usuarioId);
                    return new RuntimeException("Cliente no encontrado");
                });
    }

    public Cliente guardar(Cliente cliente) {
        Cliente resultado = clienteRepository.save(cliente);
        log.info("Cliente creado con ID: {}", resultado.getId());
        return resultado;
    }

    public Cliente actualizar(Integer id, Cliente datos) {
        log.info("Actualizando cliente con ID: {}", id);
        Cliente cliente = buscarPorId(id);
        cliente.setTipoCliente(datos.getTipoCliente());
        cliente.setDescripcion(datos.getDescripcion());
        Cliente resultado = clienteRepository.save(cliente);
        log.info("Cliente {} actualizado correctamente", id);
        return resultado;
    }

    public void eliminar(Integer id) {
        if (!clienteRepository.existsById(id)) {
            log.error("No se puede eliminar. Cliente con ID {} no existe", id);
            throw new RuntimeException("Cliente no existe");
        }
        clienteRepository.deleteById(id);
        log.info("Cliente con ID {} eliminado correctamente", id);
    }
}
