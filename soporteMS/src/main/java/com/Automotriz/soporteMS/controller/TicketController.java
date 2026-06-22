package com.Automotriz.soporteMS.controller;

import com.Automotriz.soporteMS.client.ReservaClient;
import com.Automotriz.soporteMS.dto.ReservaDTO;
import com.Automotriz.soporteMS.model.Ticket;
import com.Automotriz.soporteMS.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "Tickets de Soporte", description = "Operaciones relacionadas con los tickets de soporte")
@RequestMapping("/api/v1/tickets")
public class TicketController {

    @Autowired
    private TicketService service;

    @Autowired
    private ReservaClient reservaClient;

    @Operation(summary = "Listar todos los tickets")
    @GetMapping
    public ResponseEntity<List<Ticket>> listar() {
        List<Ticket> lista = service.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar ticket por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> buscar(@PathVariable Integer id) {
        try { return ResponseEntity.ok(service.buscarPorId(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Buscar tickets por reserva")
    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<Ticket>> porReserva(@PathVariable Integer reservaId) {
        List<Ticket> lista = service.buscarPorReserva(reservaId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar tickets por estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Ticket>> porEstado(@PathVariable String estado) {
        List<Ticket> lista = service.buscarPorEstado(estado);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener detalle del ticket con datos de la reserva")
    @GetMapping("/{id}/detalle")
    public ResponseEntity<?> detalle(@PathVariable Integer id) {
        try {
            Ticket ticket = service.buscarPorId(id);
            ReservaDTO reserva = reservaClient.obtenerReserva(ticket.getReservaId());
            return ResponseEntity.ok(java.util.Map.of("ticket", ticket, "reserva", reserva));
        } catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del ticket a crear", required = true)
    @Operation(summary = "Crear un nuevo ticket")
    @PostMapping
    public ResponseEntity<Ticket> guardar(@Valid @RequestBody Ticket ticket) {
        return ResponseEntity.ok(service.guardar(ticket));
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del ticket a actualizar", required = true)
    @Operation(summary = "Actualizar un ticket")
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> actualizar(@PathVariable Integer id, @Valid @RequestBody Ticket ticket) {
        try { return ResponseEntity.ok(service.actualizar(id, ticket)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Eliminar un ticket")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try { service.eliminar(id); return ResponseEntity.noContent().build(); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }
}
