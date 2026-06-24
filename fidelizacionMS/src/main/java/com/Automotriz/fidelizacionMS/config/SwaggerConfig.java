package com.Automotriz.fidelizacionMS.config;

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
                        .title("Gestión de Fidelización")
                        .version("1.0")
                        .description("API para gestión del programa de fidelización")
                        .contact(new Contact()
                                .name("Equipo ArriendoVehicular")
                                .email("contacto@arriendovehicular.cl")));
    }
}
