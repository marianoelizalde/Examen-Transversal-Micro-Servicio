package com.Automotriz.usuarioMS.controller;

import com.Automotriz.usuarioMS.dto.ClienteDTO;
import com.Automotriz.usuarioMS.model.Cliente;
import com.Automotriz.usuarioMS.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@Tag(name = "Clientes", description = "Operaciones relacionadas con los clientes del sistema")
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Operation(summary = "Listar todos los clientes")
    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        List<Cliente> lista = service.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar cliente por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar cliente por usuarioId")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Cliente> porUsuario(@PathVariable Integer usuarioId) {
        try {
            return ResponseEntity.ok(service.buscarPorUsuarioId(usuarioId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener DTO del cliente")
    @GetMapping("/dto/{id}")
    public ResponseEntity<ClienteDTO> obtenerDTO(@PathVariable Integer id) {
        try {
            Cliente c = service.buscarPorId(id);
            ClienteDTO dto = new ClienteDTO(c.getId(), c.getUsuario().getRut(),
                    c.getUsuario().getNombre(), c.getUsuario().getCorreo(), c.getTipoCliente());
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del cliente a crear", required = true)
    @Operation(summary = "Crear un nuevo cliente")
    @PostMapping
    public ResponseEntity<Cliente> guardar(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(service.guardar(cliente));
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del cliente a actualizar", required = true)
    @Operation(summary = "Actualizar un cliente")
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            return ResponseEntity.ok(service.actualizar(id, cliente));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un cliente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
