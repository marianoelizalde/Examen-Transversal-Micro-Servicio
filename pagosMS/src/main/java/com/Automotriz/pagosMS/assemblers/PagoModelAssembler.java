package com.Automotriz.pagosMS.assemblers;

import com.Automotriz.pagosMS.controller.PagoControllerV2;
import com.Automotriz.pagosMS.model.Pago;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<Pago, EntityModel<Pago>> {
    @Override
    public EntityModel<Pago> toModel(Pago pago) {
        return EntityModel.of(pago,
                linkTo(methodOn(PagoControllerV2.class).buscar(pago.getId())).withSelfRel(),
                linkTo(methodOn(PagoControllerV2.class).listar()).withRel("pagos"));
    }
}
