package com.Automotriz.documentosMS.config;

import com.Automotriz.documentosMS.model.*;
import com.Automotriz.documentosMS.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;

/**
 * Datos de prueba para documentosMS.
 *
 * reservaId → debe existir en reservasMS: 1, 2, 3
 * RUTs de participantes → deben existir en usuarioMS:
 *   "8114567-9"  = Mariano  (ARRENDATARIO)
 *   "9234567-8"  = Ana      (ARRENDATARIO)
 *   "11111111-1" = Pedro    (ARRENDATARIO)
 *   "15234567-8" = Carlos   (EMPLEADO)
 */
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(
            ContratoRepository contratoRepo,
            ParticipanteRepository participanteRepo) {

        return args -> {
            if (contratoRepo.count() > 0) {
                System.out.println("No se cargó nada porque ya habían datos en documentosMS");
                return;
            }

            Contrato con1 = new Contrato(null, 1, "2026-05-01", "FIRMADO",   "El cliente devuelve el vehículo en las mismas condiciones.");
            Contrato con2 = new Contrato(null, 2, "2026-05-05", "PENDIENTE", "El cliente se hace responsable de cualquier daño.");
            Contrato con3 = new Contrato(null, 3, "2026-05-08", "PENDIENTE", "Prohibido fumar dentro del vehículo.");

            contratoRepo.save(con1);
            contratoRepo.save(con2);
            contratoRepo.save(con3);

            participanteRepo.save(new Participante(null, "8114567-9",  "Mariano Elizalde", "ARRENDATARIO", true,  con1));
            participanteRepo.save(new Participante(null, "15234567-8", "Carlos Rodríguez", "EMPLEADO",     true,  con1));
            participanteRepo.save(new Participante(null, "9234567-8",  "Ana González",     "ARRENDATARIO", false, con2));
            participanteRepo.save(new Participante(null, "15234567-8", "Carlos Rodríguez", "EMPLEADO",     false, con2));
            participanteRepo.save(new Participante(null, "11111111-1", "Pedro Soto",       "ARRENDATARIO", false, con3));

            System.out.println("✅ Datos de documentosMS cargados correctamente");
        };
    }
}
