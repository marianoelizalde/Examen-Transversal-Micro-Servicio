package com.Automotriz.usuarioMS.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestión de Usuarios")
                        .version("1.0")
                        .description("API para gestión de usuarios, clientes, empleados y antecedentes")
                        .contact(new Contact()
                                .name("Equipo ArriendoVehicular")
                                .email("contacto@arriendovehicular.cl")));
    }
}
