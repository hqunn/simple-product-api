package com.lightvision.simple_product_api.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Simple Product & Order API")
                        .version("1.0")
                        .description("API Documentation for LightVision Backend Coding Test. \n\n" +
                                     "Implemented by **Dinh Hoang Quan**."))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Environment")
                ));
    }
}