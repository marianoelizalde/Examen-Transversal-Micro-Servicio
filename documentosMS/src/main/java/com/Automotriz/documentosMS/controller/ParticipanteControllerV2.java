package com.Automotriz.documentosMS.controller;

import com.Automotriz.documentosMS.assemblers.ParticipanteModelAssembler;
import com.Automotriz.documentosMS.model.Participante;
import com.Automotriz.documentosMS.service.ParticipanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/participantes")
public class ParticipanteControllerV2 {

    @Autowired private ParticipanteService participanteService;
    @Autowired private ParticipanteModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Participante>> listar() {
        List<EntityModel<Participante>> lista = participanteService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(ParticipanteControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Participante> buscar(@PathVariable Integer id) {
        return assembler.toModel(participanteService.buscarPorId(id));
    }

    @GetMapping(value = "/contrato/{contratoId}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Participante>> porContrato(@PathVariable Integer contratoId) {
        List<EntityModel<Participante>> lista = participanteService.buscarPorContrato(contratoId).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(ParticipanteControllerV2.class).porContrato(contratoId)).withSelfRel(),
                linkTo(methodOn(ParticipanteControllerV2.class).listar()).withRel("participantes"));
    }
}
