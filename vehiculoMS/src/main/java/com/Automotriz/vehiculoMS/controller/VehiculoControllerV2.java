package com.Automotriz.vehiculoMS.controller;

import com.Automotriz.vehiculoMS.assemblers.VehiculoModelAssembler;
import com.Automotriz.vehiculoMS.model.Vehiculo;
import com.Automotriz.vehiculoMS.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/vehiculos")
public class VehiculoControllerV2 {

    @Autowired private VehiculoService vehiculoService;
    @Autowired private VehiculoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Vehiculo>> listar() {
        List<EntityModel<Vehiculo>> lista = vehiculoService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(VehiculoControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Vehiculo> buscar(@PathVariable Integer id) {
        return assembler.toModel(vehiculoService.buscarPorId(id));
    }

    @GetMapping(value = "/disponibles", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Vehiculo>> disponibles() {
        List<EntityModel<Vehiculo>> lista = vehiculoService.listarDisponibles().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(VehiculoControllerV2.class).disponibles()).withSelfRel(),
                linkTo(methodOn(VehiculoControllerV2.class).listar()).withRel("vehiculos"));
    }
}
