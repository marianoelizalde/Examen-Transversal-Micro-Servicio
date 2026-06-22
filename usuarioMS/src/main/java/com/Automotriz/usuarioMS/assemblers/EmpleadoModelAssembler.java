package com.Automotriz.usuarioMS.assemblers;

import com.Automotriz.usuarioMS.controller.EmpleadoControllerV2;
import com.Automotriz.usuarioMS.model.Empleado;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EmpleadoModelAssembler implements RepresentationModelAssembler<Empleado, EntityModel<Empleado>> {
    @Override
    public EntityModel<Empleado> toModel(Empleado empleado) {
        return EntityModel.of(empleado,
                linkTo(methodOn(EmpleadoControllerV2.class).buscar(empleado.getId())).withSelfRel(),
                linkTo(methodOn(EmpleadoControllerV2.class).listar()).withRel("empleados"));
    }
}
