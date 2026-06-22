package com.Automotriz.pagosMS.controller;

import com.Automotriz.pagosMS.assemblers.PagoModelAssembler;
import com.Automotriz.pagosMS.model.Pago;
import com.Automotriz.pagosMS.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/pagos")
public class PagoControllerV2 {

    @Autowired private PagoService pagoService;
    @Autowired private PagoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Pago>> listar() {
        List<EntityModel<Pago>> lista = pagoService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(PagoControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Pago> buscar(@PathVariable Integer id) {
        return assembler.toModel(pagoService.buscarPorId(id));
    }

    @GetMapping(value = "/reserva/{reservaId}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Pago>> porReserva(@PathVariable Integer reservaId) {
        List<EntityModel<Pago>> lista = pagoService.buscarPorReserva(reservaId).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(PagoControllerV2.class).porReserva(reservaId)).withSelfRel(),
                linkTo(methodOn(PagoControllerV2.class).listar()).withRel("pagos"));
    }

    @GetMapping(value = "/estado/{estadoPago}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Pago>> porEstado(@PathVariable String estadoPago) {
        List<EntityModel<Pago>> lista = pagoService.buscarPorEstado(estadoPago).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(PagoControllerV2.class).porEstado(estadoPago)).withSelfRel(),
                linkTo(methodOn(PagoControllerV2.class).listar()).withRel("pagos"));
    }

    @GetMapping(value = "/reserva/{reservaId}/total", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Object>> totalPorReserva(@PathVariable Integer reservaId) {
        double total = pagoService.buscarPorReserva(reservaId).stream()
                .mapToDouble(Pago::getMonto).sum();
        EntityModel<Object> response = EntityModel.of(
                Map.of("reservaId", reservaId, "totalPagado", total),
                linkTo(methodOn(PagoControllerV2.class).totalPorReserva(reservaId)).withSelfRel(),
                linkTo(methodOn(PagoControllerV2.class).listar()).withRel("pagos"));
        return ResponseEntity.ok(response);
    }
}
