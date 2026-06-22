package com.Automotriz.documentosMS.controller;

import com.Automotriz.documentosMS.assemblers.ContratoModelAssembler;
import com.Automotriz.documentosMS.model.Contrato;
import com.Automotriz.documentosMS.service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/contratos")
public class ContratoControllerV2 {

    @Autowired private ContratoService contratoService;
    @Autowired private ContratoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Contrato>> listar() {
        List<EntityModel<Contrato>> contratos = contratoService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(contratos,
                linkTo(methodOn(ContratoControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Contrato> buscar(@PathVariable Integer id) {
        return assembler.toModel(contratoService.buscarPorId(id));
    }

    @GetMapping(value = "/reserva/{reservaId}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Contrato>> porReserva(@PathVariable Integer reservaId) {
        List<EntityModel<Contrato>> lista = contratoService.buscarPorReserva(reservaId).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(ContratoControllerV2.class).porReserva(reservaId)).withSelfRel(),
                linkTo(methodOn(ContratoControllerV2.class).listar()).withRel("contratos"));
    }

    @GetMapping(value = "/estado/{estado}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Contrato>> porEstado(@PathVariable String estado) {
        List<EntityModel<Contrato>> lista = contratoService.listar().stream()
                .filter(c -> c.getEstado().equalsIgnoreCase(estado))
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(ContratoControllerV2.class).porEstado(estado)).withSelfRel(),
                linkTo(methodOn(ContratoControllerV2.class).listar()).withRel("contratos"));
    }
}
