package com.Automotriz.fidelizacionMS.config;

import com.Automotriz.fidelizacionMS.model.*;
import com.Automotriz.fidelizacionMS.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;

/**
 * Datos de prueba para fidelizacionMS.
 *
 * rutCliente → debe existir en usuarioMS:
 *   "8114567-9"  = Mariano  → ORO    (5200 pts)
 *   "9234567-8"  = Ana      → PLATA  (1500 pts)
 *   "11111111-1" = Pedro    → BRONCE (300 pts)
 *   "12345678-9" = Juan     → BRONCE (0 pts)
 */
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(FidelizacionRepository fidelizacionRepo) {
        return args -> {
            if (fidelizacionRepo.count() > 0) {
                System.out.println("No se cargó nada porque ya habían datos en fidelizacionMS");
                return;
            }

            fidelizacionRepo.save(new Fidelizacion(null, "8114567-9",  5200, "ORO"));
            fidelizacionRepo.save(new Fidelizacion(null, "9234567-8",  1500, "PLATA"));
            fidelizacionRepo.save(new Fidelizacion(null, "11111111-1", 300,  "BRONCE"));
            fidelizacionRepo.save(new Fidelizacion(null, "12345678-9", 0,    "BRONCE"));

            System.out.println("✅ Datos de fidelizacionMS cargados correctamente");
        };
    }
}
