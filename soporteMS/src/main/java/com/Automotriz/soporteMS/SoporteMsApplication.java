package com.Automotriz.soporteMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SoporteMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoporteMsApplication.class, args);
    }
}

/**============================================================
  soporteMS — Puerto 8086
=============================================

── TICKETS ──────────────────────────────────────────────
GET    http://localhost:8086/api/v1/tickets                     → Lista todos los tickets
GET    http://localhost:8086/api/v1/tickets/{id}                → Busca ticket por ID
GET    http://localhost:8086/api/v1/tickets/reserva/{reservaId} → Lista tickets de una reserva
GET    http://localhost:8086/api/v1/tickets/estado/{estado}     → Filtra tickets por estado
GET    http://localhost:8086/api/v1/tickets/{id}/detalle        ★ Ticket + datos de la reserva (Feign)
POST   http://localhost:8086/api/v1/tickets                     → Crea un ticket (estado=ABIERTO)
PUT    http://localhost:8086/api/v1/tickets/{id}                → Actualiza asunto, descripción y estado
DELETE http://localhost:8086/api/v1/tickets/{id}                → Elimina un ticket

  Estados válidos: ABIERTO | EN_PROCESO | CERRADO */

