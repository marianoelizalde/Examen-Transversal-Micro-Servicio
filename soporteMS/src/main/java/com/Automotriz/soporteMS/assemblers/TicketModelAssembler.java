package com.Automotriz.soporteMS.assemblers;

import com.Automotriz.soporteMS.controller.TicketControllerV2;
import com.Automotriz.soporteMS.model.Ticket;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class TicketModelAssembler implements RepresentationModelAssembler<Ticket, EntityModel<Ticket>> {
    @Override
    public EntityModel<Ticket> toModel(Ticket ticket) {
        return EntityModel.of(ticket,
                linkTo(methodOn(TicketControllerV2.class).buscar(ticket.getId())).withSelfRel(),
                linkTo(methodOn(TicketControllerV2.class).listar()).withRel("tickets"));
    }
}
