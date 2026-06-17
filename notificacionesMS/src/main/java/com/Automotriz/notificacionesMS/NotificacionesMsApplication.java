package com.Automotriz.notificacionesMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NotificacionesMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificacionesMsApplication.class, args);
    }
}

/**============================================================
  notificacionesMS — Puerto 8087
============================================================

── NOTIFICACIONES ───────────────────────────────────────
GET    http://localhost:8087/api/v1/notificaciones              → Lista todas las notificaciones
GET    http://localhost:8087/api/v1/notificaciones/{id}         → Busca notificación por ID
GET    http://localhost:8087/api/v1/notificaciones/reserva/{reservaId}→ Lista notificaciones de una reserva
GET    http://localhost:8087/api/v1/notificaciones/estado/{estado}→ Filtra por estado
GET    http://localhost:8087/api/v1/notificaciones/{id}/detalle ★ Notificación + datos de la reserva (Feign)
POST   http://localhost:8087/api/v1/notificaciones              → Crea una notificación (estado=PENDIENTE)
PUT    http://localhost:8087/api/v1/notificaciones/{id}         → Actualiza mensaje y estado
DELETE http://localhost:8087/api/v1/notificaciones/{id}         → Elimina una notificación

  Tipos válidos:  EMAIL | SMS | PUSH
  Estados válidos: PENDIENTE | ENVIADA | FALLIDA */
