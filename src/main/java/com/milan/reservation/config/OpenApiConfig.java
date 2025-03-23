package com.milan.reservation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for OpenAPI documentation
 *
 * @author Milan Rathod
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI trainReservationApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Train Reservation System API")
                        .description("RESTful API for managing train reservations, schedules, and related services")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Milan Rathod")
                                .email("milan.rathod@example.com")
                                .url("https://github.com/milanrathod"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("Bearer Authentication", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Enter JWT token")));
    }
} 