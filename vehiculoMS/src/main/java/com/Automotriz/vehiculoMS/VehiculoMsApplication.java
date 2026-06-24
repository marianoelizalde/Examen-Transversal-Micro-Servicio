package com.Automotriz.vehiculoMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VehiculoMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(VehiculoMsApplication.class, args);
    }
}
/**============================================================
  vehiculoMS — Puerto 8081
============================================================

── VEHÍCULOS ────────────────────────────────────────────
GET    http://localhost:8081/api/v1/vehiculos                   → Lista todos los vehículos
GET    http://localhost:8081/api/v1/vehiculos/disponibles       → Lista vehículos con estado DISPONIBLE
GET    http://localhost:8081/api/v1/vehiculos/{id}              → Busca vehículo por ID
GET    http://localhost:8081/api/v1/vehiculos/patente/{patente} → Busca vehículo por patente
GET    http://localhost:8081/api/v1/vehiculos/dto/{id}          → Retorna DTO reducido (para otros MS)
POST   http://localhost:8081/api/v1/vehiculos                   → Crea un nuevo vehículo
PUT    http://localhost:8081/api/v1/vehiculos/{id}              → Actualiza un vehículo
DELETE http://localhost:8081/api/v1/vehiculos/{id}              → Elimina un vehículo

── MANTENCIONES ─────────────────────────────────────────
GET    http://localhost:8081/api/v1/mantenciones                → Lista todas las mantenciones
GET    http://localhost:8081/api/v1/mantenciones/{id}           → Busca mantención por ID
GET    http://localhost:8081/api/v1/mantenciones/vehiculo/{vehiculoId}→ Lista mantenciones de un vehículo
POST   http://localhost:8081/api/v1/mantenciones                → Crea una mantención
PUT    http://localhost:8081/api/v1/mantenciones/{id}           → Actualiza una mantención
DELETE http://localhost:8081/api/v1/mantenciones/{id}           → Elimina una mantención
*/






