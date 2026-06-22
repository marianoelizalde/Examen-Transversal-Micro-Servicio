package com.Automotriz.tarifasMS.controller;

import com.Automotriz.tarifasMS.assemblers.TarifaModelAssembler;
import com.Automotriz.tarifasMS.model.Tarifa;
import com.Automotriz.tarifasMS.service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/tarifas")
public class TarifaControllerV2 {

    @Autowired private TarifaService tarifaService;
    @Autowired private TarifaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Tarifa>> listar() {
        List<EntityModel<Tarifa>> lista = tarifaService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(TarifaControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Tarifa> buscar(@PathVariable Integer id) {
        return assembler.toModel(tarifaService.buscarPorId(id));
    }

    @GetMapping(value = "/vehiculo/{vehiculoId}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Tarifa>> porVehiculo(@PathVariable Integer vehiculoId) {
        List<EntityModel<Tarifa>> lista = tarifaService.buscarPorVehiculo(vehiculoId).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(TarifaControllerV2.class).porVehiculo(vehiculoId)).withSelfRel(),
                linkTo(methodOn(TarifaControllerV2.class).listar()).withRel("tarifas"));
    }

    @GetMapping(value = "/temporada/{temporada}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Tarifa>> porTemporada(@PathVariable String temporada) {
        List<EntityModel<Tarifa>> lista = tarifaService.buscarPorTemporada(temporada).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(TarifaControllerV2.class).porTemporada(temporada)).withSelfRel(),
                linkTo(methodOn(TarifaControllerV2.class).listar()).withRel("tarifas"));
    }

    @GetMapping(value = "/estado/{estado}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Tarifa>> porEstado(@PathVariable String estado) {
        List<EntityModel<Tarifa>> lista = tarifaService.listarActivas().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(TarifaControllerV2.class).porEstado(estado)).withSelfRel(),
                linkTo(methodOn(TarifaControllerV2.class).listar()).withRel("tarifas"));
    }
}
