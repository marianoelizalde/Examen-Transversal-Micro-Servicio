package com.Automotriz.notificacionesMS.controller;

import com.Automotriz.notificacionesMS.assemblers.NotificacionModelAssembler;
import com.Automotriz.notificacionesMS.model.Notificacion;
import com.Automotriz.notificacionesMS.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/notificaciones")
public class NotificacionControllerV2 {

    @Autowired private NotificacionService notificacionService;
    @Autowired private NotificacionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Notificacion>> listar() {
        List<EntityModel<Notificacion>> lista = notificacionService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(NotificacionControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Notificacion> buscar(@PathVariable Integer id) {
        return assembler.toModel(notificacionService.buscarPorId(id));
    }

    @GetMapping(value = "/reserva/{reservaId}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Notificacion>> porReserva(@PathVariable Integer reservaId) {
        List<EntityModel<Notificacion>> lista = notificacionService.buscarPorReserva(reservaId).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(NotificacionControllerV2.class).porReserva(reservaId)).withSelfRel(),
                linkTo(methodOn(NotificacionControllerV2.class).listar()).withRel("notificaciones"));
    }

    @GetMapping(value = "/estado/{estado}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Notificacion>> porEstado(@PathVariable String estado) {
        List<EntityModel<Notificacion>> lista = notificacionService.buscarPorEstado(estado).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(NotificacionControllerV2.class).porEstado(estado)).withSelfRel(),
                linkTo(methodOn(NotificacionControllerV2.class).listar()).withRel("notificaciones"));
    }

    @GetMapping(value = "/tipo/{tipo}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Notificacion>> porTipo(@PathVariable String tipo) {
        List<EntityModel<Notificacion>> lista = notificacionService.buscarPorEstado(tipo).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(NotificacionControllerV2.class).porTipo(tipo)).withSelfRel(),
                linkTo(methodOn(NotificacionControllerV2.class).listar()).withRel("notificaciones"));
    }
}
