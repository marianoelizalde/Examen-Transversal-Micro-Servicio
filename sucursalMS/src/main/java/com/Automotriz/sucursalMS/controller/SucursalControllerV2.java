package com.Automotriz.sucursalMS.controller;

import com.Automotriz.sucursalMS.assemblers.SucursalModelAssembler;
import com.Automotriz.sucursalMS.model.Sucursal;
import com.Automotriz.sucursalMS.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/sucursales")
public class SucursalControllerV2 {

    @Autowired private SucursalService sucursalService;
    @Autowired private SucursalModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Sucursal>> listar() {
        List<EntityModel<Sucursal>> lista = sucursalService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(SucursalControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Sucursal> buscar(@PathVariable Integer id) {
        return assembler.toModel(sucursalService.buscarPorId(id));
    }

    @GetMapping(value = "/comuna/{comuna}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Sucursal>> porComuna(@PathVariable String comuna) {
        List<EntityModel<Sucursal>> lista = sucursalService.buscarPorComuna(comuna).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(SucursalControllerV2.class).porComuna(comuna)).withSelfRel(),
                linkTo(methodOn(SucursalControllerV2.class).listar()).withRel("sucursales"));
    }
}
