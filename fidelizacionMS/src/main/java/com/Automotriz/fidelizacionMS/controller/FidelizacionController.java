package com.Automotriz.fidelizacionMS.controller;

import com.Automotriz.fidelizacionMS.client.UsuarioClient;
import com.Automotriz.fidelizacionMS.dto.UsuarioDTO;
import com.Automotriz.fidelizacionMS.model.Fidelizacion;
import com.Automotriz.fidelizacionMS.service.FidelizacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/fidelizacion")
@Tag(name = "Fidelización", description = "Operaciones relacionadas con el programa de fidelización")
public class FidelizacionController {

    @Autowired private FidelizacionService service;
    @Autowired private UsuarioClient usuarioClient;

    @Operation(summary = "Listar todos los perfiles de fidelización")
    @GetMapping
    public ResponseEntity<List<Fidelizacion>> listar() {
        List<Fidelizacion> lista = service.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar perfil por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Fidelizacion> buscar(
            @Parameter(description = "ID del perfil", required = true) @PathVariable Integer id) {
        try { return ResponseEntity.ok(service.buscarPorId(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Buscar perfil por RUT del cliente")
    @GetMapping("/rut/{rut}")
    public ResponseEntity<Fidelizacion> buscarPorRut(
            @Parameter(description = "RUT del cliente", required = true) @PathVariable String rut) {
        try { return ResponseEntity.ok(service.buscarPorRut(rut)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Buscar perfiles por nivel")
    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<Fidelizacion>> porNivel(
            @Parameter(description = "Nivel: BRONCE, PLATA, ORO, PLATINO", required = true)
            @PathVariable String nivel) {
        List<Fidelizacion> lista = service.buscarPorNivel(nivel);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener detalle con datos del usuario")
    @GetMapping("/rut/{rut}/detalle")
    public ResponseEntity<?> detalle(
            @Parameter(description = "RUT del cliente", required = true) @PathVariable String rut) {
        try {
            Fidelizacion f = service.buscarPorRut(rut);
            UsuarioDTO usuario = usuarioClient.obtenerUsuarioPorRut(rut);
            return ResponseEntity.ok(Map.of("fidelizacion", f, "usuario", usuario));
        } catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Agregar puntos al cliente", description = "Suma puntos y recalcula el nivel automáticamente")
    @ApiResponse(responseCode = "200", description = "Puntos agregados y nivel actualizado")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del perfil de fidelización a actualizar", required = true)
    @PutMapping("/rut/{rut}/puntos/{puntos}")
    public ResponseEntity<Fidelizacion> agregarPuntos(
            @Parameter(description = "RUT del cliente", required = true) @PathVariable String rut,
            @Parameter(description = "Cantidad de puntos a agregar", required = true) @PathVariable Integer puntos) {
        try { return ResponseEntity.ok(service.agregarPuntos(rut, puntos)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Crear perfil de fidelización", description = "El perfil inicia siempre en 0 puntos y nivel BRONCE")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del perfil de fidelización a crear", required = true)
    @PostMapping
    public ResponseEntity<Fidelizacion> guardar(@Valid @RequestBody Fidelizacion fidelizacion) {
        return ResponseEntity.ok(service.guardar(fidelizacion));
    }

    @Operation(summary = "Eliminar perfil de fidelización")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del perfil", required = true) @PathVariable Integer id) {
        try { service.eliminar(id); return ResponseEntity.noContent().build(); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }
}
