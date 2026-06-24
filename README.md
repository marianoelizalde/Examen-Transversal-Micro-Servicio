---------------------- ArriendoVehicular

Sistema de Gestión de Arriendo de Vehículos.

Este proyecto es una plataforma distribuida, basada en una arquitectura de microservicios, diseñada para gestionar el arriendo de vehículos de manera eficiente y escalable. El sistema aísla las distintas responsabilidades del negocio en servicios independientes, facilitando su desarrollo, mantenimiento y despliegue.

----------- Integrantes : 

- Mariano Elizalde
- Jose Tomas Martinez
- Nashky Mancilla(descansa en paz perrito ) 

-------Dominio del proyecto

El sistema administra el ciclo completo de arriendo de vehículos: gestión de clientes y empleados, control de la flota de vehículos y sus mantenciones, reservas con validación de reglas de negocio (disponibilidad del vehículo, antecedentes del cliente, fechas), pagos, contratos, soporte al cliente, tarifas y un programa de fidelización por puntos.

------Microservicios implementados

| # | Microservicio       | Puerto | Base de datos       | Responsabilidad                                   |
|---|---                  |---     |---                  |---                                                |
| 1 | usuarioMS           | 8080   | usuarios_db         | Usuarios, clientes, empleados y antecedentes      |
| 2 | vehiculoMS          | 8081   | vehiculos_db        | Vehículos y mantenciones                          |  
| 3 | sucursalMS          | 8082   | sucursales_db       | Sucursales                                        |
| 4 | reservasMS          | 8083   | reservas_db         | Reservas y reglas de negocio del arriendo         |
| 5 | documentosMS        | 8084   | documentos_db       | Contratos y participantes                         |
| 6 | pagosMS             | 8085   | pagos_db            | Pagos                                             |
| 7 | soporteMS           | 8086   | soporte_db          | Tickets de soporte                                |
| 8 | notificacionesMS    | 8087   | notificaciones_db   | Notificaciones                                    |
| 9 | fidelizacionMS      | 8088   | fidelizacion_db     | Programa de puntos y niveles de fidelización      |
| 10 | tarifasMS          | 8089   | tarifas_db          | Tarifas                                           |
| X | eureka-server       | 8761   | -                   | Service Discovery (registro de microservicios)    |
| X | api-gateway         | 9090   | -                   | Punto de entrada único, enrutamiento centralizado |

--- Rutas principales del Gateway

Todas las peticiones externas pasan por `http://localhost:9090`, que enruta internamente hacia el microservicio correspondiente vía Eureka:

| Ruta                        | Microservicio destino |
|--                           |                       |
| /api/v1/usuarios/**         | usuarioMS             |
| /api/v1/clientes/**         | usuarioMS             |
| /api/v1/empleados/**        | usuarioMS             |
| /api/v1/antecedentes/**     | usuarioMS             |
| /api/v1/vehiculos/**        | vehiculoMS            |
| /api/v1/mantenciones/**     | vehiculoMS            |
| /api/v1/sucursales/**       | sucursalMS            |
| /api/v1/reservas/**         | reservasMS            |
| /api/v1/pagos/**            | pagosMS               |
| /api/v1/contratos/**        | documentosMS          |
| /api/v1/participantes/**    | documentosMS          |
| /api/v1/notificaciones/**   | notificacionesMS      |
| /api/v1/tickets/**          | soporteMS             |
| /api/v1/tarifas/**          | tarifasMS             |
| /api/v1/fidelizacion/**     | fidelizacionMS        |

Ejemplo de uso vía Gateway:
--------
GET http://localhost:9090/api/v1/reservas
--------

------------Documentación Swagger (por microservicio)

Cada microservicio expone su propia documentación OpenAPI/Swagger en su puerto directo:

| Microservicio | URL Swagger |
|---|---|
| usuarioMS | http://localhost:8080/doc/swagger-ui/index.html |
| vehiculoMS | http://localhost:8081/doc/swagger-ui/index.html |
| sucursalMS | http://localhost:8082/doc/swagger-ui/index.html |
| reservasMS | http://localhost:8083/doc/swagger-ui/index.html |
| documentosMS | http://localhost:8084/doc/swagger-ui/index.html |
| pagosMS | http://localhost:8085/doc/swagger-ui/index.html |
| soporteMS | http://localhost:8086/doc/swagger-ui/index.html |
| notificacionesMS | http://localhost:8087/doc/swagger-ui/index.html |
| fidelizacionMS | http://localhost:8088/doc/swagger-ui/index.html |
| tarifasMS | http://localhost:8089/doc/swagger-ui/index.html |

Dashboard de Eureka (instancias registradas): http://localhost:8761

## Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado en tu entorno local:

- Java Development Kit (JDK) versión 17 o superior
- Apache Maven 3.9+ (o usar el `mvnw`/`mvnw.cmd` incluido)
- MySQL / XAMPP corriendo localmente (puerto 3306) con las bases de datos creadas
- IDE de desarrollo (IntelliJ IDEA, Eclipse o VS Code)
- Postman (o herramienta similar para probar los endpoints)

## Instrucciones de ejecución local

1. Levanta MySQL (por ejemplo, iniciando MySQL desde XAMPP Control Panel).
2. Crea las bases de datos necesarias para cada microservicio (`usuarios_db`, `vehiculos_db`, `sucursales_db`, `reservas_db`, `documentos_db`, `pagos_db`, `soporte_db`, `notificaciones_db`, `fidelizacion_db`, `tarifas_db`). Cada microservicio las crea/actualiza automáticamente vía JPA (`ddl-auto: update`) si ya existen vacías.
3. Levanta los módulos **en este orden exacto**, esperando que cada uno termine de arrancar antes de continuar con el siguiente:

```
1.  eureka-server      (puerto 8761)
2.  usuarioMS          (puerto 8080)
3.  vehiculoMS         (puerto 8081)
4.  sucursalMS         (puerto 8082)
5.  reservasMS         (puerto 8083)
6.  documentosMS       (puerto 8084)
7.  pagosMS            (puerto 8085)
8.  soporteMS          (puerto 8086)
9.  notificacionesMS   (puerto 8087)
10. fidelizacionMS     (puerto 8088)
11. tarifasMS          (puerto 8089)
12. api-gateway        (puerto 9090)
```

4. Para cada módulo, dentro de su carpeta:
```bash
mvn spring-boot:run
```
(o `mvn test` para correr solo las pruebas unitarias de ese módulo).

5. Verifica que todos los servicios se registraron correctamente entrando a:
```
http://localhost:8761
```
Deberías ver los 10 microservicios + el gateway en estado `UP`.

6. Prueba el ecosistema completo a través del Gateway, por ejemplo:
```
http://localhost:9090/api/v1/reservas
```

## Instrucciones de ejecución remota

*(Pendiente de completar una vez configurado el despliegue en Docker / Railway / Render).*

## Pruebas unitarias

Cada microservicio cuenta con pruebas unitarias (JUnit 5 + Mockito) sobre sus capas `service` y `controller`. Para ejecutarlas en un módulo específico:
```bash
cd nombreDelModulo
mvn test
```
