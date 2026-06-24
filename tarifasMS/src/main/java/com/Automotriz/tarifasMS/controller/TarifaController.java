package com.Automotriz.tarifasMS.controller;

import com.Automotriz.tarifasMS.client.VehiculoClient;
import com.Automotriz.tarifasMS.dto.VehiculoDTO;
import com.Automotriz.tarifasMS.model.Tarifa;
import com.Automotriz.tarifasMS.service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "Tarifas", description = "Operaciones relacionadas con las tarifas de vehículos")
@RequestMapping("/api/v1/tarifas")
public class TarifaController {

    @Autowired
    private TarifaService service;

    @Autowired
    private VehiculoClient vehiculoClient;

    @Operation(summary = "Listar todas las tarifas")
    @GetMapping
    public ResponseEntity<List<Tarifa>> listar() {
        List<Tarifa> lista = service.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar tarifa por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Tarifa> buscar(@PathVariable Integer id) {
        try { return ResponseEntity.ok(service.buscarPorId(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Buscar tarifas por vehículo")
    @GetMapping("/vehiculo/{vehiculoId}")
    public ResponseEntity<List<Tarifa>> porVehiculo(@PathVariable Integer vehiculoId) {
        List<Tarifa> lista = service.buscarPorVehiculo(vehiculoId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar tarifas por temporada")
    @GetMapping("/temporada/{temporada}")
    public ResponseEntity<List<Tarifa>> porTemporada(@PathVariable String temporada) {
        List<Tarifa> lista = service.buscarPorTemporada(temporada);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Listar tarifas activas")
    @GetMapping("/activas")
    public ResponseEntity<List<Tarifa>> activas() {
        List<Tarifa> lista = service.listarActivas();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    // Detalle: tarifa + datos del vehículo desde vehiculoMS via Feign
    @Operation(summary = "Obtener detalle de tarifa con datos del vehículo")
    @GetMapping("/{id}/detalle")
    public ResponseEntity<?> detalle(@PathVariable Integer id) {
        try {
            Tarifa tarifa = service.buscarPorId(id);
            VehiculoDTO vehiculo = vehiculoClient.obtenerVehiculo(tarifa.getVehiculoId());
            return ResponseEntity.ok(java.util.Map.of("tarifa", tarifa, "vehiculo", vehiculo));
        } catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del tarifa a crear", required = true)
    @Operation(summary = "Crear una nueva tarifa")
    @PostMapping
    public ResponseEntity<Tarifa> guardar(@Valid @RequestBody Tarifa tarifa) {
        return ResponseEntity.ok(service.guardar(tarifa));
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del tarifa a actualizar", required = true)
    @Operation(summary = "Actualizar una tarifa")
    @PutMapping("/{id}")
    public ResponseEntity<Tarifa> actualizar(@PathVariable Integer id, @Valid @RequestBody Tarifa tarifa) {
        try { return ResponseEntity.ok(service.actualizar(id, tarifa)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Eliminar una tarifa")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try { service.eliminar(id); return ResponseEntity.noContent().build(); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }
}