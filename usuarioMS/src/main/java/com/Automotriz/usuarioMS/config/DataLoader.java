package com.Automotriz.usuarioMS.config;

import com.Automotriz.usuarioMS.model.*;
import com.Automotriz.usuarioMS.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;

/**
 * Datos de prueba para usuarioMS.
 *
 * RUTs generados (usados por reservasMS y fidelizacionMS):
 *   "8114567-9"  → Mariano  (cliente)
 *   "9234567-8"  → Ana      (cliente)
 *   "11111111-1" → Pedro    (cliente)
 *   "12345678-9" → Juan     (cliente)
 *   "15234567-8" → Carlos   (empleado, sucursalId=1)
 */
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(
            UsuarioRepository usuarioRepo,
            ClienteRepository clienteRepo,
            EmpleadoRepository empleadoRepo,
            AntecedentesRepository antecedentesRepo) {

        return args -> {
            if (usuarioRepo.count() > 0) {
                System.out.println("No se cargó nada porque ya habían datos en usuarioMS");
                return;
            }

            // Usuarios
            Usuario u1 = new Usuario(null, "8114567-9",  "Mariano Elizalde",  "mariano@gmail.com",  "+56912345678", "Av. Siempre Viva 123", 1, "clave123");
            Usuario u2 = new Usuario(null, "9234567-8",  "Ana González",      "ana@gmail.com",      "+56987654321", "Calle Principal 456",  1, "clave456");
            Usuario u3 = new Usuario(null, "11111111-1", "Pedro Soto",        "pedro@gmail.com",    "+56933333333", "Av. Grecia 100",        1, "clave789");
            Usuario u4 = new Usuario(null, "12345678-9", "Juan Pérez",        "juan@gmail.com",     "+56944444444", "Calle Falsa 123",       1, "clave000");
            Usuario u5 = new Usuario(null, "15234567-8", "Carlos Rodríguez",  "carlos@motornya.cl", "+56911111111", "Av. Central 789",       2, "empleado123");

            usuarioRepo.save(u1); usuarioRepo.save(u2); usuarioRepo.save(u3);
            usuarioRepo.save(u4); usuarioRepo.save(u5);

            // Clientes
            Cliente c1 = new Cliente(null, "NATURAL", "Cliente particular",     u1);
            Cliente c2 = new Cliente(null, "NATURAL", "Cliente particular",     u2);
            Cliente c3 = new Cliente(null, "NATURAL", "Cliente particular",     u3);
            Cliente c4 = new Cliente(null, "EMPRESA", "Empresa de transportes", u4);

            clienteRepo.save(c1); clienteRepo.save(c2);
            clienteRepo.save(c3); clienteRepo.save(c4);

            // Empleado (sucursalId=1 debe existir en sucursalMS)
            Empleado e1 = new Empleado(null, "Vendedor sucursal centro", 1, "Banco Estado / 123456789", u5);
            empleadoRepo.save(e1);

            // Antecedentes
            antecedentesRepo.save(new Antecedentes(null, "VIGENTE",    0, c1));
            antecedentesRepo.save(new Antecedentes(null, "VIGENTE",    1, c2));
            antecedentesRepo.save(new Antecedentes(null, "SUSPENDIDA", 3, c3));
            antecedentesRepo.save(new Antecedentes(null, "VIGENTE",    0, c4));

            System.out.println("✅ Datos de usuarioMS cargados correctamente");
        };
    }
}
