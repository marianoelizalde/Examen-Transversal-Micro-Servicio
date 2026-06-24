package com.Automotriz.notificacionesMS.controller;

import com.Automotriz.notificacionesMS.client.ReservaClient;
import com.Automotriz.notificacionesMS.dto.ReservaDTO;
import com.Automotriz.notificacionesMS.model.Notificacion;
import com.Automotriz.notificacionesMS.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/notificaciones")
@Tag(name = "Notificaciones", description = "Operaciones relacionadas con las notificaciones del sistema")
public class NotificacionController {

    @Autowired private NotificacionService service;
    @Autowired private ReservaClient reservaClient;

    @Operation(summary = "Listar todas las notificaciones")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay notificaciones registradas")
    })
    @GetMapping
    public ResponseEntity<List<Notificacion>> listar() {
        List<Notificacion> lista = service.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar notificación por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificación encontrada"),
        @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> buscar(
            @Parameter(description = "ID de la notificación", required = true) @PathVariable Integer id) {
        try { return ResponseEntity.ok(service.buscarPorId(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Buscar notificaciones por reserva")
    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<Notificacion>> porReserva(
            @Parameter(description = "ID de la reserva", required = true) @PathVariable Integer reservaId) {
        List<Notificacion> lista = service.buscarPorReserva(reservaId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar notificaciones por estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Notificacion>> porEstado(
            @Parameter(description = "Estado de la notificación: PENDIENTE, ENVIADA, FALLIDA", required = true)
            @PathVariable String estado) {
        List<Notificacion> lista = service.buscarPorEstado(estado);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar notificaciones por tipo")
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Notificacion>> porTipo(
            @Parameter(description = "Tipo de notificación: EMAIL, SMS, PUSH", required = true)
            @PathVariable String tipo) {
        List<Notificacion> lista = service.buscarPorTipo(tipo);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener detalle de la notificación con datos de la reserva")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @GetMapping("/{id}/detalle")
    public ResponseEntity<?> detalle(
            @Parameter(description = "ID de la notificación", required = true) @PathVariable Integer id) {
        try {
            Notificacion notificacion = service.buscarPorId(id);
            ReservaDTO reserva = reservaClient.obtenerReserva(notificacion.getReservaId());
            return ResponseEntity.ok(Map.of("notificacion", notificacion, "reserva", reserva));
        } catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Crear una nueva notificación", description = "El estado se establece automáticamente en PENDIENTE")
    @ApiResponse(responseCode = "200", description = "Notificación creada exitosamente")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la notificación a crear", required = true)
    @PostMapping
    public ResponseEntity<Notificacion> guardar(@Valid @RequestBody Notificacion notificacion) {
        return ResponseEntity.ok(service.guardar(notificacion));
    }

    @Operation(summary = "Actualizar una notificación")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la notificación a actualizar", required = true)
    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> actualizar(
            @Parameter(description = "ID de la notificación", required = true) @PathVariable Integer id,
            @Valid @RequestBody Notificacion notificacion) {
        try { return ResponseEntity.ok(service.actualizar(id, notificacion)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Eliminar una notificación")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la notificación", required = true) @PathVariable Integer id) {
        try { service.eliminar(id); return ResponseEntity.noContent().build(); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }
}
