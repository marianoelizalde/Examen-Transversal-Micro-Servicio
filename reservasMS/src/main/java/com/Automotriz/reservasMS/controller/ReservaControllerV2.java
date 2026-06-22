package com.Automotriz.reservasMS.controller;

import com.Automotriz.reservasMS.assemblers.ReservaModelAssembler;
import com.Automotriz.reservasMS.model.Reserva;
import com.Automotriz.reservasMS.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/reservas")
public class ReservaControllerV2 {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ReservaModelAssembler assembler;

    // ── CRUD base con HATEOAS ─────────────────────────────────────────────────

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Reserva>> listar() {
        List<EntityModel<Reserva>> reservas = reservaService.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(reservas,
                linkTo(methodOn(ReservaControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Reserva> buscar(@PathVariable Integer id) {
        Reserva reserva = reservaService.buscarPorId(id);
        return assembler.toModel(reserva);
    }

    // ── Método 1: Reservas de un cliente en una fecha específica ──────────────
    @GetMapping(value = "/cliente/{rutCliente}/fecha/{fecha}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Reserva>> getReservasPorClienteYFecha(
            @PathVariable String rutCliente,
            @PathVariable String fecha) {
        List<EntityModel<Reserva>> reservas = reservaService.buscarPorCliente(rutCliente).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(reservas,
                linkTo(methodOn(ReservaControllerV2.class).getReservasPorClienteYFecha(rutCliente, fecha)).withSelfRel(),
                linkTo(methodOn(ReservaControllerV2.class).listar()).withRel("reservas"));
    }

    // ── Método 2: Reservas de una patente con un estado específico ────────────
    @GetMapping(value = "/patente/{patente}/estado/{estado}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Reserva>> getReservasPorPatenteYEstado(
            @PathVariable String patente,
            @PathVariable String estado) {
        List<EntityModel<Reserva>> reservas = reservaService.buscarPorCliente(patente).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(reservas,
                linkTo(methodOn(ReservaControllerV2.class).getReservasPorPatenteYEstado(patente, estado)).withSelfRel(),
                linkTo(methodOn(ReservaControllerV2.class).listar()).withRel("reservas"));
    }

    // ── Método 3: Reservas de un cliente entre dos fechas ─────────────────────
    @GetMapping(value = "/cliente/{rutCliente}/fechas", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Reserva>> getReservasPorClienteEntreFechas(
            @PathVariable String rutCliente,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        List<EntityModel<Reserva>> reservas = reservaService.buscarPorCliente(rutCliente).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(reservas,
                linkTo(methodOn(ReservaControllerV2.class).getReservasPorClienteEntreFechas(rutCliente, fechaInicio, fechaFin)).withSelfRel(),
                linkTo(methodOn(ReservaControllerV2.class).listar()).withRel("reservas"));
    }

    // ── Método 4: Reservas de una patente entre dos fechas ────────────────────
    @GetMapping(value = "/patente/{patente}/fechas", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Reserva>> getReservasPorPatenteEntreFechas(
            @PathVariable String patente,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        List<EntityModel<Reserva>> reservas = reservaService.buscarPorCliente(patente).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(reservas,
                linkTo(methodOn(ReservaControllerV2.class).getReservasPorPatenteEntreFechas(patente, fechaInicio, fechaFin)).withSelfRel(),
                linkTo(methodOn(ReservaControllerV2.class).listar()).withRel("reservas"));
    }

    // ── Método 5: Total de reservas en una patente específica ─────────────────
    @GetMapping(value = "/patente/{patente}/total", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Object>> getTotalReservasPorPatente(@PathVariable String patente) {
        long total = reservaService.buscarPorCliente(patente).size();
        EntityModel<Object> response = EntityModel.of(
                java.util.Map.of("patente", patente, "totalReservas", total),
                linkTo(methodOn(ReservaControllerV2.class).getTotalReservasPorPatente(patente)).withSelfRel(),
                linkTo(methodOn(ReservaControllerV2.class).listar()).withRel("reservas"));
        return ResponseEntity.ok(response);
    }
}
