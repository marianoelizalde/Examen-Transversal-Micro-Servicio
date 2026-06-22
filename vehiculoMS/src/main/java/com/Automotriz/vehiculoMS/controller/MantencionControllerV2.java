package com.Automotriz.vehiculoMS.controller;

import com.Automotriz.vehiculoMS.assemblers.MantencionModelAssembler;
import com.Automotriz.vehiculoMS.model.Mantencion;
import com.Automotriz.vehiculoMS.service.MantencionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/mantenciones")
public class MantencionControllerV2 {

    @Autowired private MantencionService mantencionService;
    @Autowired private MantencionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Mantencion>> listar() {
        List<EntityModel<Mantencion>> lista = mantencionService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(MantencionControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Mantencion> buscar(@PathVariable Integer id) {
        return assembler.toModel(mantencionService.buscarPorId(id));
    }

    @GetMapping(value = "/vehiculo/{vehiculoId}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Mantencion>> porVehiculo(@PathVariable Integer vehiculoId) {
        List<EntityModel<Mantencion>> lista = mantencionService.buscarPorVehiculo(vehiculoId).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(MantencionControllerV2.class).porVehiculo(vehiculoId)).withSelfRel(),
                linkTo(methodOn(MantencionControllerV2.class).listar()).withRel("mantenciones"));
    }
}
