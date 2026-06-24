package com.Automotriz.reservasMS.controller;

import com.Automotriz.reservasMS.dto.ReservaDetalleDTO;
import com.Automotriz.reservasMS.model.Reserva;
import com.Automotriz.reservasMS.service.ReservaService;
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

@RestController
@RequestMapping("/api/v1/reservas")
@Tag(name = "Reservas", description = "Operaciones relacionadas con las reservas de vehículos")
public class ReservaController {

    @Autowired
    private ReservaService service;

    @Operation(summary = "Listar todas las reservas", description = "Retorna una lista de todas las reservas registradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de reservas obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay reservas registradas")
    })
    @GetMapping
    public ResponseEntity<List<Reserva>> listar() {
        List<Reserva> lista = service.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar reserva por ID", description = "Retorna una reserva específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reserva encontrada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> buscar(
            @Parameter(description = "ID de la reserva", required = true) @PathVariable Integer id) {
        try { return ResponseEntity.ok(service.buscarPorId(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Buscar reservas por cliente", description = "Retorna todas las reservas de un cliente por su RUT")
    @ApiResponse(responseCode = "200", description = "Reservas del cliente obtenidas exitosamente")
    @GetMapping("/cliente/{rutCliente}")
    public ResponseEntity<List<Reserva>> porCliente(
            @Parameter(description = "RUT del cliente", required = true) @PathVariable String rutCliente) {
        List<Reserva> lista = service.buscarPorCliente(rutCliente);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar reservas por estado", description = "Retorna todas las reservas con un estado específico")
    @ApiResponse(responseCode = "200", description = "Reservas filtradas exitosamente")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Reserva>> porEstado(
            @Parameter(description = "Estado de la reserva: PENDIENTE, ACTIVA, FINALIZADA, CANCELADA", required = true)
            @PathVariable String estado) {
        List<Reserva> lista = service.buscarPorEstado(estado);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener detalle completo de una reserva", description = "Retorna la reserva con datos del cliente, vehículo y sucursal obtenidos via Feign")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle de reserva obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @GetMapping("/{id}/detalle")
    public ResponseEntity<ReservaDetalleDTO> detalle(
            @Parameter(description = "ID de la reserva", required = true) @PathVariable Integer id) {
        try { return ResponseEntity.ok(service.obtenerDetalle(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Crear una nueva reserva", description = "Crea una reserva nueva. El estado se establece automáticamente en PENDIENTE")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reserva creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o regla de negocio no cumplida")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del reserva a crear", required = true)
    @PostMapping
    public ResponseEntity<Reserva> guardar(@Valid @RequestBody Reserva reserva) {
        return ResponseEntity.ok(service.guardar(reserva));
    }

    @Operation(summary = "Actualizar una reserva", description = "Actualiza los datos de una reserva existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reserva actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del reserva a actualizar", required = true)
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizar(
            @Parameter(description = "ID de la reserva", required = true) @PathVariable Integer id,
            @Valid @RequestBody Reserva reserva) {
        try { return ResponseEntity.ok(service.actualizar(id, reserva)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Eliminar una reserva", description = "Elimina una reserva por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reserva eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la reserva", required = true) @PathVariable Integer id) {
        try { service.eliminar(id); return ResponseEntity.noContent().build(); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }
}
