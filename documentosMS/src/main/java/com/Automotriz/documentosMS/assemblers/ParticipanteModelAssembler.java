package com.Automotriz.documentosMS.assemblers;

import com.Automotriz.documentosMS.controller.ParticipanteControllerV2;
import com.Automotriz.documentosMS.model.Participante;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ParticipanteModelAssembler implements RepresentationModelAssembler<Participante, EntityModel<Participante>> {
    @Override
    public EntityModel<Participante> toModel(Participante participante) {
        return EntityModel.of(participante,
                linkTo(methodOn(ParticipanteControllerV2.class).buscar(participante.getId())).withSelfRel(),
                linkTo(methodOn(ParticipanteControllerV2.class).listar()).withRel("participantes"));
    }
}
