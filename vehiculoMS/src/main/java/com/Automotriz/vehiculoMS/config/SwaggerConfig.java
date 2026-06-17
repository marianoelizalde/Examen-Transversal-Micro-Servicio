package com.Automotriz.vehiculoMS.config;

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
                        .title("Gestión de Vehículos")
                        .version("1.0")
                        .description("API para gestión de vehículos y mantenciones")
                        .contact(new Contact()
                                .name("Equipo ArriendoVehicular")
                                .email("contacto@arriendovehicular.cl")));
    }
}
