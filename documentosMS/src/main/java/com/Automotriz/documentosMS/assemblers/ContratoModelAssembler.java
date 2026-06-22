package com.Automotriz.documentosMS.assemblers;

import com.Automotriz.documentosMS.controller.ContratoControllerV2;
import com.Automotriz.documentosMS.model.Contrato;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ContratoModelAssembler implements RepresentationModelAssembler<Contrato, EntityModel<Contrato>> {
    @Override
    public EntityModel<Contrato> toModel(Contrato contrato) {
        return EntityModel.of(contrato,
                linkTo(methodOn(ContratoControllerV2.class).buscar(contrato.getId())).withSelfRel(),
                linkTo(methodOn(ContratoControllerV2.class).listar()).withRel("contratos"));
    }
}
