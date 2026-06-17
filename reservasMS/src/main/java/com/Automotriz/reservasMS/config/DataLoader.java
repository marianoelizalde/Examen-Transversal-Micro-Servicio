package com.Automotriz.reservasMS.config;

import com.Automotriz.reservasMS.model.*;
import com.Automotriz.reservasMS.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(ReservaRepository reservaRepo) {

        return args -> {

            if (reservaRepo.count() > 0) {
                System.out.println("No se cargó nada porque ya habían datos");
            } else {

                // ⚠️ Los rut deben existir en usuarioMS
                // ⚠️ Las patentes deben existir en vehiculoMS
                // ⚠️ Los sucursalId deben existir en sucursalMS
                Reserva r1 = new Reserva(null, "12345678-9", "ABCD12", 1, "2026-05-01", "2026-05-07", "ACTIVA");
                Reserva r2 = new Reserva(null, "98765432-1", "WXYZ98", 2, "2026-05-05", "2026-05-10", "PENDIENTE");
                Reserva r3 = new Reserva(null, "11111111-1", "EFGH34", 3, "2026-05-08", "2026-05-15", "PENDIENTE");
                Reserva r4 = new Reserva(null, "12345678-9", "MNOP78", 1, "2026-04-01", "2026-04-05", "FINALIZADA");

                reservaRepo.save(r1);
                reservaRepo.save(r2);
                reservaRepo.save(r3);
                reservaRepo.save(r4);

                System.out.println("✅ Datos de reservasMS cargados correctamente");
            }
        };
    }
}
