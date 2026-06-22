package com.Automotriz.sucursalMS.config;

import com.Automotriz.sucursalMS.model.*;
import com.Automotriz.sucursalMS.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;

/**
 * Datos de prueba para sucursalMS.
 *
 * IDs generados en orden (usados por reservasMS y usuarioMS):
 *   1 → Sucursal Centro       (empleado Carlos trabaja aquí)
 *   2 → Sucursal Providencia
 *   3 → Sucursal Ñuñoa
 *   4 → Sucursal Maipú
 *   5 → Sucursal Las Condes
 *   6 → Sucursal Vitacura
 */
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(SucursalRepository sucursalRepo) {
        return args -> {
            if (sucursalRepo.count() > 0) {
                System.out.println("No se cargó nada porque ya habían datos en sucursalMS");
                return;
            }

            sucursalRepo.save(new Sucursal(null, "Sucursal Centro",      "Av. Libertador 123",       "Santiago",    8));
            sucursalRepo.save(new Sucursal(null, "Sucursal Providencia",  "Av. Providencia 1234",     "Providencia", 5));
            sucursalRepo.save(new Sucursal(null, "Sucursal Ñuñoa",        "Av. Irarrázaval 890",      "Ñuñoa",       4));
            sucursalRepo.save(new Sucursal(null, "Sucursal Maipú",        "Av. Pajaritos 456",        "Maipú",       6));
            sucursalRepo.save(new Sucursal(null, "Sucursal Las Condes",   "Av. Apoquindo 789",        "Las Condes",  7));
            sucursalRepo.save(new Sucursal(null, "Sucursal Vitacura",     "Av. Nueva Costanera 4000", "Vitacura",    3));

            System.out.println("✅ Datos de sucursalMS cargados correctamente");
        };
    }
}
