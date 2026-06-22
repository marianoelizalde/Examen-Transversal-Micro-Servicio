package com.Automotriz.vehiculoMS.controller;

import com.Automotriz.vehiculoMS.model.Mantencion;
import com.Automotriz.vehiculoMS.service.MantencionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@Tag(name = "Mantenciones", description = "Operaciones relacionadas con las mantenciones de vehículos")
@RequestMapping("/api/v1/mantenciones")
public class MantencionController {

    @Autowired
    private MantencionService service;

    @Operation(summary = "Listar todas las mantenciones")
    @GetMapping
    public ResponseEntity<List<Mantencion>> listar() {
        List<Mantencion> lista = service.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar mantención por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Mantencion> buscar(@PathVariable Integer id) {
        try { return ResponseEntity.ok(service.buscarPorId(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Buscar mantenciones por vehículo")
    @GetMapping("/vehiculo/{vehiculoId}")
    public ResponseEntity<List<Mantencion>> porVehiculo(@PathVariable Integer vehiculoId) {
        List<Mantencion> lista = service.buscarPorVehiculo(vehiculoId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del mantención a crear", required = true)
    @Operation(summary = "Crear una nueva mantención")
    @PostMapping
    public ResponseEntity<Mantencion> guardar(@RequestBody Mantencion mantencion) {
        return ResponseEntity.ok(service.guardar(mantencion));
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del mantención a actualizar", required = true)
    @Operation(summary = "Actualizar una mantención")
    @PutMapping("/{id}")
    public ResponseEntity<Mantencion> actualizar(@PathVariable Integer id, @RequestBody Mantencion mantencion) {
        try { return ResponseEntity.ok(service.actualizar(id, mantencion)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Eliminar una mantención")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try { service.eliminar(id); return ResponseEntity.noContent().build(); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }
}
