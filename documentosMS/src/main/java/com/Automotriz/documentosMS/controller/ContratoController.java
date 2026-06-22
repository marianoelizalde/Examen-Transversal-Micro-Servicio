package com.Automotriz.documentosMS.controller;

import com.Automotriz.documentosMS.client.ReservaClient;
import com.Automotriz.documentosMS.dto.ReservaDTO;
import com.Automotriz.documentosMS.model.Contrato;
import com.Automotriz.documentosMS.service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "Contratos", description = "Operaciones relacionadas con los contratos")
@RequestMapping("/api/v1/contratos")
public class ContratoController {

    @Autowired
    private ContratoService service;

    @Autowired
    private ReservaClient reservaClient;

    @Operation(summary = "Listar todos los contratos")
    @GetMapping
    public ResponseEntity<List<Contrato>> listar() {
        List<Contrato> lista = service.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar contrato por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Contrato> buscar(@PathVariable Integer id) {
        try { return ResponseEntity.ok(service.buscarPorId(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Buscar contratos por reserva")
    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<Contrato>> porReserva(@PathVariable Integer reservaId) {
        List<Contrato> lista = service.buscarPorReserva(reservaId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    // Detalle: contrato + datos de la reserva
    @Operation(summary = "Obtener detalle del contrato con datos de la reserva")
    @GetMapping("/{id}/detalle")
    public ResponseEntity<?> detalle(@PathVariable Integer id) {
        try {
            Contrato contrato = service.buscarPorId(id);
            ReservaDTO reserva = reservaClient.obtenerReserva(contrato.getReservaId());
            return ResponseEntity.ok(java.util.Map.of("contrato", contrato, "reserva", reserva));
        } catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del contrato a crear", required = true)
    @Operation(summary = "Crear un nuevo contrato")
    @PostMapping
    public ResponseEntity<Contrato> guardar(@Valid @RequestBody Contrato contrato) {
        return ResponseEntity.ok(service.guardar(contrato));
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del contrato a actualizar", required = true)
    @Operation(summary = "Actualizar un contrato")
    @PutMapping("/{id}")
    public ResponseEntity<Contrato> actualizar(@PathVariable Integer id, @Valid @RequestBody Contrato contrato) {
        try { return ResponseEntity.ok(service.actualizar(id, contrato)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Eliminar un contrato")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try { service.eliminar(id); return ResponseEntity.noContent().build(); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }
}
