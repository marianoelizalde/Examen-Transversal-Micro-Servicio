package com.Automotriz.vehiculoMS.config;

import com.Automotriz.vehiculoMS.model.*;
import com.Automotriz.vehiculoMS.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;
import java.time.LocalDate;

/**
 * Datos de prueba para vehiculoMS.
 *
 * Patentes generadas (usadas por reservasMS y tarifasMS):
 *   vehiculoId 1 → "ABCD12"  Toyota Corolla   (DISPONIBLE)
 *   vehiculoId 2 → "EFGH34"  Honda Civic      (DISPONIBLE)
 *   vehiculoId 3 → "IJKL56"  Mazda3           (EN_MANTENCION)
 *   vehiculoId 4 → "MNOP78"  Hyundai Tucson   (DISPONIBLE)
 */
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(
            VehiculoRepository vehiculoRepo,
            MantencionRepository mantencionRepo) {

        return args -> {
            if (vehiculoRepo.count() > 0) {
                System.out.println("No se cargó nada porque ya habían datos en vehiculoMS");
                return;
            }

            Vehiculo v1 = new Vehiculo(null, "Corolla", "XEI", "Toyota",  2022, "DISPONIBLE",    "ABCD12");
            Vehiculo v2 = new Vehiculo(null, "Civic",   "EX",  "Honda",   2021, "DISPONIBLE",    "EFGH34");
            Vehiculo v3 = new Vehiculo(null, "Mazda3",  "GT",  "Mazda",   2023, "EN_MANTENCION", "IJKL56");
            Vehiculo v4 = new Vehiculo(null, "Tucson",  "GL",  "Hyundai", 2023, "DISPONIBLE",    "MNOP78");

            vehiculoRepo.save(v1); vehiculoRepo.save(v2);
            vehiculoRepo.save(v3); vehiculoRepo.save(v4);

            // Mantención del Mazda3 (LocalDate porque el modelo usa LocalDate)
            mantencionRepo.save(new Mantencion(null, LocalDate.of(2026, 5, 10), 150000.0, v3));

            System.out.println("✅ Datos de vehiculoMS cargados correctamente");
        };
    }
}
