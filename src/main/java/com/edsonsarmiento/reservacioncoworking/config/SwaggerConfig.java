package com.edsonsarmiento.reservacioncoworking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.description("Documentación de reservación de salas de coworking");

        return new OpenAPI().addServersItem(server).info(
                new Info()
                        .title("Gestión de reservaciones de salas de coworking")
                        .description("Microservicio de reservaciones de salas de coworking")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Edson Ariel Argueta Sarmiento")
                                .email("edsonareil.sarmiento@gmail.com")
                        )
        );
    }
}
