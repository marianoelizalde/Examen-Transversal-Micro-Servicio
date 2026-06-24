package com.Automotriz.pagosMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PagosMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(PagosMsApplication.class, args);
    }
}
/**============================================================
  pagosMS — Puerto 8085
============================================================

── PAGOS ────────────────────────────────────────────────
GET    http://localhost:8085/api/v1/pagos                       → Lista todos los pagos
GET    http://localhost:8085/api/v1/pagos/{id}                  → Busca pago por ID
GET    http://localhost:8085/api/v1/pagos/reserva/{reservaId}   → Lista pagos de una reserva
GET    http://localhost:8085/api/v1/pagos/estado/{estadoPago}   → Filtra pagos por estado
GET    http://localhost:8085/api/v1/pagos/{id}/detalle          ★ Pago + datos de la reserva (Feign)
POST   http://localhost:8085/api/v1/pagos                       → Crea un pago (estadoPago=PENDIENTE)
PUT    http://localhost:8085/api/v1/pagos/{id}                  → Actualiza monto, estado y método
DELETE http://localhost:8085/api/v1/pagos/{id}                  → Elimina un pago

  Estados válidos: PENDIENTE | PAGADO | RECHAZADO
  Métodos válidos: DEBITO | CREDITO | TRANSFERENCIA | EFECTIVO/*/