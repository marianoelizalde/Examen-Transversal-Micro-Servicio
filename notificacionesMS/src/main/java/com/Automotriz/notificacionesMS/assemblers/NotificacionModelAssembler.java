package com.Automotriz.notificacionesMS.assemblers;

import com.Automotriz.notificacionesMS.controller.NotificacionControllerV2;
import com.Automotriz.notificacionesMS.model.Notificacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class NotificacionModelAssembler implements RepresentationModelAssembler<Notificacion, EntityModel<Notificacion>> {
    @Override
    public EntityModel<Notificacion> toModel(Notificacion notificacion) {
        return EntityModel.of(notificacion,
                linkTo(methodOn(NotificacionControllerV2.class).buscar(notificacion.getId())).withSelfRel(),
                linkTo(methodOn(NotificacionControllerV2.class).listar()).withRel("notificaciones"));
    }
}
