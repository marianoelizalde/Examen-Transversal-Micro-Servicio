package com.Automotriz.notificacionesMS.controller;

import com.Automotriz.notificacionesMS.client.ReservaClient;
import com.Automotriz.notificacionesMS.dto.ReservaDTO;
import com.Automotriz.notificacionesMS.model.Notificacion;
import com.Automotriz.notificacionesMS.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "Notificaciones", description = "Operaciones relacionadas con las notificaciones")
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    @Autowired
    private ReservaClient reservaClient;

    @Operation(summary = "Listar todas las notificaciones")
    @GetMapping
    public ResponseEntity<List<Notificacion>> listar() {
        List<Notificacion> lista = service.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar notificación por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> buscar(@PathVariable Integer id) {
        try { return ResponseEntity.ok(service.buscarPorId(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Buscar notificaciones por reserva")
    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<Notificacion>> porReserva(@PathVariable Integer reservaId) {
        List<Notificacion> lista = service.buscarPorReserva(reservaId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar notificaciones por estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Notificacion>> porEstado(@PathVariable String estado) {
        List<Notificacion> lista = service.buscarPorEstado(estado);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener detalle de la notificación con datos de la reserva")
    @GetMapping("/{id}/detalle")
    public ResponseEntity<?> detalle(@PathVariable Integer id) {
        try {
            Notificacion notificacion = service.buscarPorId(id);
            ReservaDTO reserva = reservaClient.obtenerReserva(notificacion.getReservaId());
            return ResponseEntity.ok(java.util.Map.of("notificacion", notificacion, "reserva", reserva));
        } catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del notificación a crear", required = true)
    @Operation(summary = "Crear una nueva notificación")
    @PostMapping
    public ResponseEntity<Notificacion> guardar(@Valid @RequestBody Notificacion notificacion) {
        return ResponseEntity.ok(service.guardar(notificacion));
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del notificación a actualizar", required = true)
    @Operation(summary = "Actualizar una notificación")
    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> actualizar(@PathVariable Integer id, @Valid @RequestBody Notificacion notificacion) {
        try { return ResponseEntity.ok(service.actualizar(id, notificacion)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Eliminar una notificación")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try { service.eliminar(id); return ResponseEntity.noContent().build(); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }
}
