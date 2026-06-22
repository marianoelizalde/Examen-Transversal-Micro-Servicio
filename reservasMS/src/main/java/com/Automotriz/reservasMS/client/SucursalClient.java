package com.Automotriz.reservasMS.client;

import com.Automotriz.reservasMS.dto.SucursalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sucursalMS")
public interface SucursalClient {

    @GetMapping("/api/v1/sucursales/dto/{id}")
    SucursalDTO obtenerSucursal(@PathVariable("id") Integer id);
}
