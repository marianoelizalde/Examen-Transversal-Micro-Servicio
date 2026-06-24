package com.Automotriz.documentosMS.client;

import com.Automotriz.documentosMS.dto.ReservaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reservasMS")
public interface ReservaClient {

    @GetMapping("/api/v1/reservas/{id}")
    ReservaDTO obtenerReserva(@PathVariable("id") Integer id);
}
