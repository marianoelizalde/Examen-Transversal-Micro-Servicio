package com.Automotriz.usuarioMS.controller;

import com.Automotriz.usuarioMS.assemblers.ClienteModelAssembler;
import com.Automotriz.usuarioMS.model.Cliente;
import com.Automotriz.usuarioMS.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/clientes")
public class ClienteControllerV2 {

    @Autowired private ClienteService clienteService;
    @Autowired private ClienteModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Cliente>> listar() {
        List<EntityModel<Cliente>> lista = clienteService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(ClienteControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Cliente> buscar(@PathVariable Integer id) {
        return assembler.toModel(clienteService.buscarPorId(id));
    }

    @GetMapping(value = "/tipo/{tipoCliente}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Cliente>> porTipo(@PathVariable String tipoCliente) {
        List<EntityModel<Cliente>> lista = clienteService.listar().stream()
                .filter(c -> c.getTipoCliente().equalsIgnoreCase(tipoCliente))
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(ClienteControllerV2.class).porTipo(tipoCliente)).withSelfRel(),
                linkTo(methodOn(ClienteControllerV2.class).listar()).withRel("clientes"));
    }
}
