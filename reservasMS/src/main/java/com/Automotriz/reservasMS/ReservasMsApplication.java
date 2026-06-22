package com.Automotriz.reservasMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReservasMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReservasMsApplication.class, args);
    }
}

/**============================================================
  reservasMS — Puerto 8083
============================================================

── RESERVAS ─────────────────────────────────────────────
GET    http://localhost:8083/api/v1/reservas                    → Lista todas las reservas
GET    http://localhost:8083/api/v1/reservas/{id}               → Busca reserva por ID
GET    http://localhost:8083/api/v1/reservas/cliente/{rutCliente}→ Lista reservas de un cliente (por RUT)
GET    http://localhost:8083/api/v1/reservas/estado/{estado}    → Filtra reservas por estado
GET    http://localhost:8083/api/v1/reservas/{id}/detalle       ★ Reserva + cliente + vehículo + sucursal (Feign)
POST   http://localhost:8083/api/v1/reservas                    → Crea una nueva reserva (estado=PENDIENTE)
PUT    http://localhost:8083/api/v1/reservas/{id}               → Actualiza una reserva
DELETE http://localhost:8083/api/v1/reservas/{id}               → Elimina una reserva

  Estados válidos: PENDIENTE | ACTIVA | FINALIZADA | CANCELADA/* */

