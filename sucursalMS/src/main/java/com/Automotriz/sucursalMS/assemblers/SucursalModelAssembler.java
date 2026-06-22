package com.Automotriz.sucursalMS.assemblers;

import com.Automotriz.sucursalMS.controller.SucursalControllerV2;
import com.Automotriz.sucursalMS.model.Sucursal;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>> {
    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal) {
        return EntityModel.of(sucursal,
                linkTo(methodOn(SucursalControllerV2.class).buscar(sucursal.getId())).withSelfRel(),
                linkTo(methodOn(SucursalControllerV2.class).listar()).withRel("sucursales"));
    }
}
