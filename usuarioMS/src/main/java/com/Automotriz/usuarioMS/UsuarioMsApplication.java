package com.Automotriz.usuarioMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UsuarioMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsuarioMsApplication.class, args);
    }
}

/**============================================================
  usuarioMS — Puerto 8080
============================================================

── USUARIOS ─────────────────────────────────────────────
GET    http://localhost:8080/api/v1/usuarios                    → Lista todos los usuarios
GET    http://localhost:8080/api/v1/usuarios/clientes           → Lista usuarios tipo 1 (CLIENTE)
GET    http://localhost:8080/api/v1/usuarios/empleados          → Lista usuarios tipo 2 (EMPLEADO)
GET    http://localhost:8080/api/v1/usuarios/{id}               → Busca usuario por ID
GET    http://localhost:8080/api/v1/usuarios/rut/{rut}          → Busca usuario por RUT
GET    http://localhost:8080/api/v1/usuarios/dto/{id}           → Retorna DTO reducido (para otros MS)
POST   http://localhost:8080/api/v1/usuarios                    → Crea un nuevo usuario
PUT    http://localhost:8080/api/v1/usuarios/{id}               → Actualiza un usuario
DELETE http://localhost:8080/api/v1/usuarios/{id}               → Elimina un usuario 



── CLIENTES ─────────────────────────────────────────────
GET    http://localhost:8080/api/v1/clientes                    → Lista todos los clientes
GET    http://localhost:8080/api/v1/clientes/{id}               → Busca cliente por ID
GET    http://localhost:8080/api/v1/clientes/usuario/{usuarioId}→ Busca cliente por usuarioId
GET    http://localhost:8080/api/v1/clientes/dto/{id}           → Retorna DTO reducido del cliente
POST   http://localhost:8080/api/v1/clientes                    → Crea un nuevo cliente
PUT    http://localhost:8080/api/v1/clientes/{id}               → Actualiza un cliente
DELETE http://localhost:8080/api/v1/clientes/{id}               → Elimina un cliente

── EMPLEADOS ────────────────────────────────────────────
GET    http://localhost:8080/api/v1/empleados                   → Lista todos los empleados
GET    http://localhost:8080/api/v1/empleados/{id}              → Busca empleado por ID
GET    http://localhost:8080/api/v1/empleados/usuario/{usuarioId}→ Busca empleado por usuarioId
GET    http://localhost:8080/api/v1/empleados/sucursal/{sucursalId}→ Lista empleados de una sucursal
GET    http://localhost:8080/api/v1/empleados/dto/{id}          → Retorna DTO reducido del empleado
POST   http://localhost:8080/api/v1/empleados                   → Crea un nuevo empleado
PUT    http://localhost:8080/api/v1/empleados/{id}              → Actualiza un empleado
DELETE http://localhost:8080/api/v1/empleados/{id}              → Elimina un empleado

── ANTECEDENTES ─────────────────────────────────────────
GET    http://localhost:8080/api/v1/antecedentes                → Lista todos los antecedentes
GET    http://localhost:8080/api/v1/antecedentes/{id}           → Busca antecedentes por ID
GET    http://localhost:8080/api/v1/antecedentes/cliente/{clienteId}→ Busca antecedentes por clienteId
POST   http://localhost:8080/api/v1/antecedentes                → Crea antecedentes
PUT    http://localhost:8080/api/v1/antecedentes/{id}           → Actualiza antecedentes
DELETE http://localhost:8080/api/v1/antecedentes/{id}           → Elimina antecedentes

*/