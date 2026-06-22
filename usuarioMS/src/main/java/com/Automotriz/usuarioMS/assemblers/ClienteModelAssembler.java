package com.Automotriz.usuarioMS.assemblers;

import com.Automotriz.usuarioMS.controller.ClienteControllerV2;
import com.Automotriz.usuarioMS.model.Cliente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> {
    @Override
    public EntityModel<Cliente> toModel(Cliente cliente) {
        return EntityModel.of(cliente,
                linkTo(methodOn(ClienteControllerV2.class).buscar(cliente.getId())).withSelfRel(),
                linkTo(methodOn(ClienteControllerV2.class).listar()).withRel("clientes"));
    }
}
