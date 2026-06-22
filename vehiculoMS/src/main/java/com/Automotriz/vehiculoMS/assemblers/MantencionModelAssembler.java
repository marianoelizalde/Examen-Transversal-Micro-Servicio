package com.Automotriz.vehiculoMS.assemblers;

import com.Automotriz.vehiculoMS.controller.MantencionControllerV2;
import com.Automotriz.vehiculoMS.model.Mantencion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class MantencionModelAssembler implements RepresentationModelAssembler<Mantencion, EntityModel<Mantencion>> {
    @Override
    public EntityModel<Mantencion> toModel(Mantencion mantencion) {
        return EntityModel.of(mantencion,
                linkTo(methodOn(MantencionControllerV2.class).buscar(mantencion.getId())).withSelfRel(),
                linkTo(methodOn(MantencionControllerV2.class).listar()).withRel("mantenciones"));
    }
}
