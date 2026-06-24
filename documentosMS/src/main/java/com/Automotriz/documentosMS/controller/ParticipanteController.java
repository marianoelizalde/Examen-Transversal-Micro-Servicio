package com.Automotriz.documentosMS.controller;

import com.Automotriz.documentosMS.model.Participante;
import com.Automotriz.documentosMS.service.ParticipanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@Tag(name = "Participantes", description = "Operaciones relacionadas con los participantes de contratos")
@RequestMapping("/api/v1/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService service;

    @Operation(summary = "Listar todos los participantes")
    @GetMapping
    public ResponseEntity<List<Participante>> listar() {
        List<Participante> lista = service.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar participante por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Participante> buscar(@PathVariable Integer id) {
        try { return ResponseEntity.ok(service.buscarPorId(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Buscar participantes por contrato")
    @GetMapping("/contrato/{contratoId}")
    public ResponseEntity<List<Participante>> porContrato(@PathVariable Integer contratoId) {
        List<Participante> lista = service.buscarPorContrato(contratoId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del participante a crear", required = true)
    @Operation(summary = "Agregar participante a un contrato")
    @PostMapping
    public ResponseEntity<Participante> guardar(@RequestBody Participante participante) {
        return ResponseEntity.ok(service.guardar(participante));
    }

    @Operation(summary = "Eliminar un participante")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try { service.eliminar(id); return ResponseEntity.noContent().build(); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }
}
