package com.Automotriz.usuarioMS.controller;

import com.Automotriz.usuarioMS.model.Antecedentes;
import com.Automotriz.usuarioMS.service.AntecedentesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@Tag(name = "Antecedentes", description = "Operaciones relacionadas con los antecedentes de clientes")
@RequestMapping("/api/v1/antecedentes")
public class AntecedentesController {

    @Autowired
    private AntecedentesService service;

    @Operation(summary = "Listar todos los antecedentes")
    @GetMapping
    public ResponseEntity<List<Antecedentes>> listar() {
        List<Antecedentes> lista = service.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar antecedentes por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Antecedentes> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar antecedentes por cliente")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Antecedentes> porCliente(@PathVariable Integer clienteId) {
        try {
            return ResponseEntity.ok(service.buscarPorCliente(clienteId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del antecedentes a crear", required = true)
    @Operation(summary = "Crear antecedentes para un cliente")
    @PostMapping
    public ResponseEntity<Antecedentes> guardar(@RequestBody Antecedentes antecedentes) {
        return ResponseEntity.ok(service.guardar(antecedentes));
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del antecedentes a actualizar", required = true)
    @Operation(summary = "Actualizar antecedentes")
    @PutMapping("/{id}")
    public ResponseEntity<Antecedentes> actualizar(@PathVariable Integer id, @RequestBody Antecedentes antecedentes) {
        try {
            return ResponseEntity.ok(service.actualizar(id, antecedentes));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar antecedentes")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
