package com.Automotriz.fidelizacionMS.controller;

import com.Automotriz.fidelizacionMS.assemblers.FidelizacionModelAssembler;
import com.Automotriz.fidelizacionMS.model.Fidelizacion;
import com.Automotriz.fidelizacionMS.service.FidelizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/fidelizaciones")
public class FidelizacionControllerV2 {

    @Autowired private FidelizacionService fidelizacionService;
    @Autowired private FidelizacionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Fidelizacion>> listar() {
        List<EntityModel<Fidelizacion>> lista = fidelizacionService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(FidelizacionControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Fidelizacion> buscar(@PathVariable Integer id) {
        return assembler.toModel(fidelizacionService.buscarPorId(id));
    }

    @GetMapping(value = "/nivel/{nivelSocio}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Fidelizacion>> porNivel(@PathVariable String nivelSocio) {
        List<EntityModel<Fidelizacion>> lista = fidelizacionService.buscarPorNivel(nivelSocio).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(FidelizacionControllerV2.class).porNivel(nivelSocio)).withSelfRel(),
                linkTo(methodOn(FidelizacionControllerV2.class).listar()).withRel("fidelizaciones"));
    }
}
