package com.Automotriz.reservasMS.client;

import com.Automotriz.reservasMS.dto.VehiculoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "vehiculoMS")
public interface VehiculoClient {

    @GetMapping("/api/v1/vehiculos/dto/{id}")
    VehiculoDTO obtenerVehiculo(@PathVariable("id") Integer id);

    @GetMapping("/api/v1/vehiculos/patente/{patente}")
    VehiculoDTO obtenerVehiculoPorPatente(@PathVariable("patente") String patente);
}
