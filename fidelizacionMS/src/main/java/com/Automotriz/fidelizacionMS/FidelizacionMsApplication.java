package com.Automotriz.fidelizacionMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FidelizacionMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(FidelizacionMsApplication.class, args);
    }
}

/**============================================================
  fidelizacionMS — Puerto 8088
============================================================

── FIDELIZACIÓN ─────────────────────────────────────────
GET    http://localhost:8088/api/v1/fidelizacion                → Lista todos los registros
GET    http://localhost:8088/api/v1/fidelizacion/{id}           → Busca por ID
GET    http://localhost:8088/api/v1/fidelizacion/rut/{rut}      → Busca por RUT del cliente
GET    http://localhost:8088/api/v1/fidelizacion/nivel/{nivel}  → Filtra por nivel (BRONCE/PLATA/ORO/PLATINO)
GET    http://localhost:8088/api/v1/fidelizacion/rut/{rut}/detalle ★ Fidelización + datos del usuario (Feign)
PUT    http://localhost:8088/api/v1/fidelizacion/rut/{rut}/puntos/{puntos}→ Suma puntos y recalcula nivel
POST   http://localhost:8088/api/v1/fidelizacion                → Crea perfil (siempre inicia en 0pts/BRONCE)
DELETE http://localhost:8088/api/v1/fidelizacion/{id}           → Elimina un perfil

  Niveles: BRONCE (0-999) | PLATA (1000-4999) | ORO (5000-9999) | PLATINO (10000+)*/


