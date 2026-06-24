package com.Automotriz.soporteMS.config;

import com.Automotriz.soporteMS.model.*;
import com.Automotriz.soporteMS.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(TicketRepository ticketRepo) {

        return args -> {

            if (ticketRepo.count() > 0) {
                System.out.println("No se cargó nada porque ya habían datos");
            } else {

                Ticket t1 = new Ticket(null, 1, "Problema con el vehículo",     "El aire acondicionado no funciona",          "CERRADO",    "2026-05-01");
                Ticket t2 = new Ticket(null, 2, "Consulta sobre el arriendo",   "Necesito extender el plazo de devolución",   "ABIERTO",    "2026-05-05");
                Ticket t3 = new Ticket(null, 3, "Daño en el vehículo",          "Rayón en la puerta delantera derecha",       "EN_PROCESO", "2026-05-08");
                Ticket t4 = new Ticket(null, 1, "Cobro incorrecto",             "Me cobraron más de lo acordado",             "CERRADO",    "2026-04-02");

                ticketRepo.save(t1);
                ticketRepo.save(t2);
                ticketRepo.save(t3);
                ticketRepo.save(t4);

                System.out.println(" Datos de soporteMS cargados correctamente");
            }
        };
    }
}
