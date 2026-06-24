package com.Automotriz.sucursalMS.controller;

import com.Automotriz.sucursalMS.model.Sucursal;
import com.Automotriz.sucursalMS.service.SucursalService;
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
@RequestMapping("/api/v1/sucursales")
@Tag(name = "Sucursales", description = "Operaciones relacionadas con las sucursales del sistema")
public class SucursalController {

    @Autowired private SucursalService service;

    @Operation(summary = "Listar todas las sucursales")
    @GetMapping
    public ResponseEntity<List<Sucursal>> listar() {
        List<Sucursal> lista = service.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar sucursal por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal encontrada"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> buscar(
            @Parameter(description = "ID de la sucursal", required = true) @PathVariable Integer id) {
        try { return ResponseEntity.ok(service.buscarPorId(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Buscar sucursales por comuna")
    @GetMapping("/comuna/{comuna}")
    public ResponseEntity<List<Sucursal>> porComuna(
            @Parameter(description = "Nombre de la comuna", required = true) @PathVariable String comuna) {
        List<Sucursal> lista = service.buscarPorComuna(comuna);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Crear una nueva sucursal")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del sucursal a crear", required = true)
    @PostMapping
    public ResponseEntity<Sucursal> guardar(@Valid @RequestBody Sucursal sucursal) {
        return ResponseEntity.ok(service.guardar(sucursal));
    }

    @Operation(summary = "Actualizar una sucursal")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del sucursal a actualizar", required = true)
    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizar(
            @Parameter(description = "ID de la sucursal", required = true) @PathVariable Integer id,
            @Valid @RequestBody Sucursal sucursal) {
        try { return ResponseEntity.ok(service.actualizar(id, sucursal)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Eliminar una sucursal")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la sucursal", required = true) @PathVariable Integer id) {
        try { service.eliminar(id); return ResponseEntity.noContent().build(); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }
}
