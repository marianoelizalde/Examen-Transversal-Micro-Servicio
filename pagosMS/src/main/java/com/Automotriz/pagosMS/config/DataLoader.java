package com.Automotriz.pagosMS.config;

import com.Automotriz.pagosMS.model.*;
import com.Automotriz.pagosMS.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(PagoRepository pagoRepo) {
        return args -> {
            if (pagoRepo.count() > 0) {
                System.out.println("No se cargó nada porque ya habían datos en pagosMS");
                return;
            }

            pagoRepo.save(new Pago(null, 1, 150000.0, "2026-05-01", "PAGADO",    "DEBITO"));
            pagoRepo.save(new Pago(null, 2, 280000.0, "2026-05-05", "PENDIENTE", "TRANSFERENCIA"));
            pagoRepo.save(new Pago(null, 3, 210000.0, "2026-05-08", "PENDIENTE", "CREDITO"));
            pagoRepo.save(new Pago(null, 4, 120000.0, "2026-04-01", "PAGADO",    "EFECTIVO"));

            System.out.println("✅ Datos de pagosMS cargados correctamente");
        };
    }
}