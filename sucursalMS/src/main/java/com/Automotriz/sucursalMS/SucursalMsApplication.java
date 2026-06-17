package com.Automotriz.sucursalMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SucursalMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SucursalMsApplication.class, args);
    }
}

/**============================================================
  sucursalMS — Puerto 8082
============================================================

── SUCURSALES ───────────────────────────────────────────
GET    http://localhost:8082/api/v1/sucursales                  → Lista todas las sucursales
GET    http://localhost:8082/api/v1/sucursales/{id}             → Busca sucursal por ID
GET    http://localhost:8082/api/v1/sucursales/comuna/{comuna}  → Filtra sucursales por comuna
GET    http://localhost:8082/api/v1/sucursales/dto/{id}         → Retorna DTO reducido (para otros MS)
POST   http://localhost:8082/api/v1/sucursales                  → Crea una nueva sucursal
PUT    http://localhost:8082/api/v1/sucursales/{id}             → Actualiza una sucursal
DELETE http://localhost:8082/api/v1/sucursales/{id}             → Elimina una sucursal*/

