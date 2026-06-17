package com.Automotriz.vehiculoMS.controller;

import com.Automotriz.vehiculoMS.model.Vehiculo;
import com.Automotriz.vehiculoMS.service.VehiculoService;
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
@RequestMapping("/api/v1/vehiculos")
@Tag(name = "Vehículos", description = "Operaciones relacionadas con los vehículos del sistema")
public class VehiculoController {

    @Autowired private VehiculoService service;

    @Operation(summary = "Listar todos los vehículos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay vehículos registrados")
    })
    @GetMapping
    public ResponseEntity<List<Vehiculo>> listar() {
        List<Vehiculo> lista = service.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Listar vehículos disponibles")
    @GetMapping("/disponibles")
    public ResponseEntity<List<Vehiculo>> listarDisponibles() {
        List<Vehiculo> lista = service.listarDisponibles();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar vehículo por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehículo encontrado"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> buscar(
            @Parameter(description = "ID del vehículo", required = true) @PathVariable Integer id) {
        try { return ResponseEntity.ok(service.buscarPorId(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Buscar vehículo por patente")
    @GetMapping("/patente/{patente}")
    public ResponseEntity<Vehiculo> buscarPorPatente(
            @Parameter(description = "Patente del vehículo", required = true) @PathVariable String patente) {
        try { return ResponseEntity.ok(service.buscarPorPatente(patente)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Crear un nuevo vehículo")
    @ApiResponse(responseCode = "200", description = "Vehículo creado exitosamente")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del vehículo a crear", required = true)
    @PostMapping
    public ResponseEntity<Vehiculo> guardar(@Valid @RequestBody Vehiculo vehiculo) {
        return ResponseEntity.ok(service.guardar(vehiculo));
    }

    @Operation(summary = "Actualizar un vehículo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehículo actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del vehículo a actualizar", required = true)
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizar(
            @Parameter(description = "ID del vehículo", required = true) @PathVariable Integer id,
            @Valid @RequestBody Vehiculo vehiculo) {
        try { return ResponseEntity.ok(service.actualizar(id, vehiculo)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Eliminar un vehículo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Vehículo eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del vehículo", required = true) @PathVariable Integer id) {
        try { service.eliminar(id); return ResponseEntity.noContent().build(); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }
}
