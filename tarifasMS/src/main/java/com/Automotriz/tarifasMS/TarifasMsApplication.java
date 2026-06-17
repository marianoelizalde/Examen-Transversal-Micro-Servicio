package com.Automotriz.tarifasMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients  // ← ¿tienes esta línea?
public class TarifasMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(TarifasMsApplication.class, args);
    }
}


/** ============================================================
  tarifasMS — Puerto 8089
============================================================

── TARIFAS ──────────────────────────────────────────────
GET    http://localhost:8089/api/v1/tarifas                     → Lista todas las tarifas
GET    http://localhost:8089/api/v1/tarifas/{id}                → Busca tarifa por ID
GET    http://localhost:8089/api/v1/tarifas/vehiculo/{vehiculoId}→ Lista tarifas de un vehículo
GET    http://localhost:8089/api/v1/tarifas/temporada/{temporada}→ Filtra por temporada
GET    http://localhost:8089/api/v1/tarifas/activas             → Lista solo tarifas con estado ACTIVO
GET    http://localhost:8089/api/v1/tarifas/{id}/detalle        ★ Tarifa + datos del vehículo (Feign)
POST   http://localhost:8089/api/v1/tarifas                     → Crea una tarifa
PUT    http://localhost:8089/api/v1/tarifas/{id}                → Actualiza una tarifa
DELETE http://localhost:8089/api/v1/tarifas/{id}                → Elimina una tarifa

  Temporadas válidas: BAJA | NORMAL | ALTA
  Estados válidos:    ACTIVO | INACTIVO */
