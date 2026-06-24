package com.Automotriz.tarifasMS.client;

import com.Automotriz.tarifasMS.dto.VehiculoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "vehiculoMS")
public interface VehiculoClient {

    @GetMapping("/api/v1/vehiculos/dto/{id}")
    VehiculoDTO obtenerVehiculo(@PathVariable("id") Integer id);
}