package com.Automotriz.documentosMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DocumentosMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DocumentosMsApplication.class, args);
    }
}

/**============================================================
  documentosMS — Puerto 8084
============================================================

── CONTRATOS ────────────────────────────────────────────
GET    http://localhost:8084/api/v1/contratos                   → Lista todos los contratos
GET    http://localhost:8084/api/v1/contratos/{id}              → Busca contrato por ID
GET    http://localhost:8084/api/v1/contratos/reserva/{reservaId}→ Lista contratos de una reserva
GET    http://localhost:8084/api/v1/contratos/{id}/detalle      ★ Contrato + datos de la reserva (Feign)
POST   http://localhost:8084/api/v1/contratos                   → Crea un contrato (estado=PENDIENTE)
PUT    http://localhost:8084/api/v1/contratos/{id}              → Actualiza estado y cláusulas
DELETE http://localhost:8084/api/v1/contratos/{id}              → Elimina un contrato

  Estados válidos: PENDIENTE | FIRMADO | ANULADO

── PARTICIPANTES ────────────────────────────────────────
GET    http://localhost:8084/api/v1/participantes               → Lista todos los participantes
GET    http://localhost:8084/api/v1/participantes/{id}          → Busca participante por ID
GET    http://localhost:8084/api/v1/participantes/contrato/{contratoId}→ Lista participantes de un contrato
POST   http://localhost:8084/api/v1/participantes               → Agrega un participante a un contrato
DELETE http://localhost:8084/api/v1/participantes/{id}          → Elimina un participante

  Roles válidos: ARRENDATARIO | EMPLEADO */
