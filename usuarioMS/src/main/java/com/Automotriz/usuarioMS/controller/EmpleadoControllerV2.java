package com.Automotriz.usuarioMS.controller;

import com.Automotriz.usuarioMS.assemblers.EmpleadoModelAssembler;
import com.Automotriz.usuarioMS.model.Empleado;
import com.Automotriz.usuarioMS.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/empleados")
public class EmpleadoControllerV2 {

    @Autowired private EmpleadoService empleadoService;
    @Autowired private EmpleadoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Empleado>> listar() {
        List<EntityModel<Empleado>> lista = empleadoService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(EmpleadoControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Empleado> buscar(@PathVariable Integer id) {
        return assembler.toModel(empleadoService.buscarPorId(id));
    }

    @GetMapping(value = "/sucursal/{sucursalId}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Empleado>> porSucursal(@PathVariable Integer sucursalId) {
        List<EntityModel<Empleado>> lista = empleadoService.buscarPorSucursal(sucursalId).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(EmpleadoControllerV2.class).porSucursal(sucursalId)).withSelfRel(),
                linkTo(methodOn(EmpleadoControllerV2.class).listar()).withRel("empleados"));
    }
}
