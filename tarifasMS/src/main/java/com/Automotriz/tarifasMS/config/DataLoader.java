package com.Automotriz.tarifasMS.config;

import com.Automotriz.tarifasMS.model.*;
import com.Automotriz.tarifasMS.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;

/**
 * Datos de prueba para tarifasMS.
 *
 * vehiculoId → debe existir en vehiculoMS (IDs autogenerados en orden):
 *   1 = Toyota Corolla  (ABCD12)
 *   2 = Honda Civic     (EFGH34)
 *   3 = Mazda3          (IJKL56)
 *   4 = Hyundai Tucson  (MNOP78)
 */
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(TarifaRepository tarifaRepo) {
        return args -> {
            if (tarifaRepo.count() > 0) {
                System.out.println("No se cargó nada porque ya habían datos en tarifasMS");
                return;
            }

            // Toyota Corolla
            tarifaRepo.save(new Tarifa(null, 1, 30000.0, "BAJA",   "ACTIVO"));
            tarifaRepo.save(new Tarifa(null, 1, 45000.0, "NORMAL", "ACTIVO"));
            tarifaRepo.save(new Tarifa(null, 1, 65000.0, "ALTA",   "ACTIVO"));
            // Honda Civic
            tarifaRepo.save(new Tarifa(null, 2, 25000.0, "BAJA",   "ACTIVO"));
            tarifaRepo.save(new Tarifa(null, 2, 38000.0, "NORMAL", "ACTIVO"));
            // Mazda3 (en mantención, tarifa inactiva)
            tarifaRepo.save(new Tarifa(null, 3, 72000.0, "ALTA",   "INACTIVO"));
            // Hyundai Tucson
            tarifaRepo.save(new Tarifa(null, 4, 50000.0, "NORMAL", "ACTIVO"));
            tarifaRepo.save(new Tarifa(null, 4, 70000.0, "ALTA",   "ACTIVO"));

            System.out.println("✅ Datos de tarifasMS cargados correctamente");
        };
    }
}
