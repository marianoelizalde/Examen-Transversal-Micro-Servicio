package com.Automotriz.pagosMS.controller;

import com.Automotriz.pagosMS.client.ReservaClient;
import com.Automotriz.pagosMS.dto.ReservaDTO;
import com.Automotriz.pagosMS.model.Pago;
import com.Automotriz.pagosMS.service.PagoService;
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
@RequestMapping("/api/v1/pagos")
@Tag(name = "Pagos", description = "Operaciones relacionadas con los pagos del sistema")
public class PagoController {

    @Autowired private PagoService service;
    @Autowired private ReservaClient reservaClient;

    @Operation(summary = "Listar todos los pagos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay pagos registrados")
    })
    @GetMapping
    public ResponseEntity<List<Pago>> listar() {
        List<Pago> lista = service.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar pago por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago encontrado"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pago> buscar(
            @Parameter(description = "ID del pago", required = true) @PathVariable Integer id) {
        try { return ResponseEntity.ok(service.buscarPorId(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Buscar pagos por reserva")
    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<Pago>> porReserva(
            @Parameter(description = "ID de la reserva", required = true) @PathVariable Integer reservaId) {
        List<Pago> lista = service.buscarPorReserva(reservaId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar pagos por estado")
    @GetMapping("/estado/{estadoPago}")
    public ResponseEntity<List<Pago>> porEstado(
            @Parameter(description = "Estado del pago: PENDIENTE, PAGADO, RECHAZADO", required = true)
            @PathVariable String estadoPago) {
        List<Pago> lista = service.buscarPorEstado(estadoPago);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener detalle del pago con datos de la reserva")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/{id}/detalle")
    public ResponseEntity<?> detalle(
            @Parameter(description = "ID del pago", required = true) @PathVariable Integer id) {
        try {
            Pago pago = service.buscarPorId(id);
            ReservaDTO reserva = reservaClient.obtenerReserva(pago.getReservaId());
            return ResponseEntity.ok(Map.of("pago", pago, "reserva", reserva));
        } catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Crear un nuevo pago", description = "El estadoPago se establece automáticamente en PENDIENTE")
    @ApiResponse(responseCode = "200", description = "Pago creado exitosamente")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del pago a crear", required = true)
    @PostMapping
    public ResponseEntity<Pago> guardar(@Valid @RequestBody Pago pago) {
        return ResponseEntity.ok(service.guardar(pago));
    }

    @Operation(summary = "Actualizar un pago")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del pago a actualizar", required = true)
    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizar(
            @Parameter(description = "ID del pago", required = true) @PathVariable Integer id,
            @Valid @RequestBody Pago pago) {
        try { return ResponseEntity.ok(service.actualizar(id, pago)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Eliminar un pago")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del pago", required = true) @PathVariable Integer id) {
        try { service.eliminar(id); return ResponseEntity.noContent().build(); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }
}
