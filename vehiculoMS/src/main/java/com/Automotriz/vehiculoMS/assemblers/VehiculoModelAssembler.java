package com.Automotriz.vehiculoMS.assemblers;

import com.Automotriz.vehiculoMS.controller.VehiculoControllerV2;
import com.Automotriz.vehiculoMS.model.Vehiculo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class VehiculoModelAssembler implements RepresentationModelAssembler<Vehiculo, EntityModel<Vehiculo>> {
    @Override
    public EntityModel<Vehiculo> toModel(Vehiculo vehiculo) {
        return EntityModel.of(vehiculo,
                linkTo(methodOn(VehiculoControllerV2.class).buscar(vehiculo.getId())).withSelfRel(),
                linkTo(methodOn(VehiculoControllerV2.class).listar()).withRel("vehiculos"));
    }
}
