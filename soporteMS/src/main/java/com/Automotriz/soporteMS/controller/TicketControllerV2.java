package com.Automotriz.soporteMS.controller;

import com.Automotriz.soporteMS.assemblers.TicketModelAssembler;
import com.Automotriz.soporteMS.model.Ticket;
import com.Automotriz.soporteMS.service.TicketService;
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
@RequestMapping("/api/v2/tickets")
public class TicketControllerV2 {

    @Autowired private TicketService ticketService;
    @Autowired private TicketModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Ticket>> listar() {
        List<EntityModel<Ticket>> lista = ticketService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(TicketControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Ticket> buscar(@PathVariable Integer id) {
        return assembler.toModel(ticketService.buscarPorId(id));
    }

    @GetMapping(value = "/reserva/{reservaId}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Ticket>> porReserva(@PathVariable Integer reservaId) {
        List<EntityModel<Ticket>> lista = ticketService.buscarPorReserva(reservaId).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(TicketControllerV2.class).porReserva(reservaId)).withSelfRel(),
                linkTo(methodOn(TicketControllerV2.class).listar()).withRel("tickets"));
    }

    @GetMapping(value = "/estado/{estado}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Ticket>> porEstado(@PathVariable String estado) {
        List<EntityModel<Ticket>> lista = ticketService.buscarPorEstado(estado).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(TicketControllerV2.class).porEstado(estado)).withSelfRel(),
                linkTo(methodOn(TicketControllerV2.class).listar()).withRel("tickets"));
    }

    @GetMapping(value = "/reserva/{reservaId}/total", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Object>> totalPorReserva(@PathVariable Integer reservaId) {
        long total = ticketService.buscarPorReserva(reservaId).size();
        EntityModel<Object> response = EntityModel.of(
                Map.of("reservaId", reservaId, "totalTickets", total),
                linkTo(methodOn(TicketControllerV2.class).totalPorReserva(reservaId)).withSelfRel(),
                linkTo(methodOn(TicketControllerV2.class).listar()).withRel("tickets"));
        return ResponseEntity.ok(response);
    }
}
