package com.Automotriz.reservasMS.client;

import com.Automotriz.reservasMS.dto.AntecedentesDTO;
import com.Automotriz.reservasMS.dto.ClienteDTO;
import com.Automotriz.reservasMS.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuarioMS")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/dto/{id}")
    UsuarioDTO obtenerUsuario(@PathVariable("id") Integer id);

    @GetMapping("/api/v1/usuarios/rut/{rut}")
    UsuarioDTO obtenerUsuarioPorRut(@PathVariable("rut") String rut);

    @GetMapping("/api/v1/clientes/usuario/{usuarioId}")
    ClienteDTO obtenerClientePorUsuarioId(@PathVariable("usuarioId") Integer usuarioId);

    @GetMapping("/api/v1/antecedentes/cliente/{clienteId}")
    AntecedentesDTO obtenerAntecedentesPorClienteId(@PathVariable("clienteId") Integer clienteId);
}
