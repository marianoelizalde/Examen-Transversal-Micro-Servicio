package com.Automotriz.usuarioMS.controller;

import com.Automotriz.usuarioMS.assemblers.UsuarioModelAssembler;
import com.Automotriz.usuarioMS.model.Usuario;
import com.Automotriz.usuarioMS.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/usuarios")
public class UsuarioControllerV2 {

    @Autowired private UsuarioService usuarioService;
    @Autowired private UsuarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Usuario>> listar() {
        List<EntityModel<Usuario>> lista = usuarioService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(UsuarioControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Usuario> buscar(@PathVariable Integer id) {
        return assembler.toModel(usuarioService.buscarPorId(id));
    }

    @GetMapping(value = "/tipo/{tipo}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Usuario>> porTipo(@PathVariable Integer tipo) {
        List<EntityModel<Usuario>> lista = usuarioService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(UsuarioControllerV2.class).porTipo(tipo)).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).listar()).withRel("usuarios"));
    }
}
