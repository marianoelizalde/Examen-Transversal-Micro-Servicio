package com.Automotriz.fidelizacionMS.client;

import com.Automotriz.fidelizacionMS.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuarioMS")
public interface UsuarioClient {
    @GetMapping("/api/v1/usuarios/rut/{rut}")
    UsuarioDTO obtenerUsuarioPorRut(@PathVariable("rut") String rut);
}
