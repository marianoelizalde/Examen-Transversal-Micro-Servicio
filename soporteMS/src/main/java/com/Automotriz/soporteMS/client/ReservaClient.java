package com.Automotriz.soporteMS.client;

import com.Automotriz.soporteMS.dto.ReservaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reservasMS")
public interface ReservaClient {
    @GetMapping("/api/v1/reservas/{id}")
    ReservaDTO obtenerReserva(@PathVariable("id") Integer id);
}
