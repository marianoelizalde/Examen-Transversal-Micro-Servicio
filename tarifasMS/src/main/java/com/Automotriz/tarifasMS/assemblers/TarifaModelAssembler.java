package com.Automotriz.tarifasMS.assemblers;

import com.Automotriz.tarifasMS.controller.TarifaControllerV2;
import com.Automotriz.tarifasMS.model.Tarifa;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class TarifaModelAssembler implements RepresentationModelAssembler<Tarifa, EntityModel<Tarifa>> {
    @Override
    public EntityModel<Tarifa> toModel(Tarifa tarifa) {
        return EntityModel.of(tarifa,
                linkTo(methodOn(TarifaControllerV2.class).buscar(tarifa.getId())).withSelfRel(),
                linkTo(methodOn(TarifaControllerV2.class).listar()).withRel("tarifas"));
    }
}
