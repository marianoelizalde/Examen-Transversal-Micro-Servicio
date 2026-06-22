package com.Automotriz.fidelizacionMS.assemblers;

import com.Automotriz.fidelizacionMS.controller.FidelizacionControllerV2;
import com.Automotriz.fidelizacionMS.model.Fidelizacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class FidelizacionModelAssembler implements RepresentationModelAssembler<Fidelizacion, EntityModel<Fidelizacion>> {
    @Override
    public EntityModel<Fidelizacion> toModel(Fidelizacion fidelizacion) {
        return EntityModel.of(fidelizacion,
                linkTo(methodOn(FidelizacionControllerV2.class).buscar(fidelizacion.getId())).withSelfRel(),
                linkTo(methodOn(FidelizacionControllerV2.class).listar()).withRel("fidelizaciones"));
    }
}
