package com.lightvision.simple_product_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class SimpleProductApiApplication {

    public static void main(String[] args) {
        // Set JVM timezone to UTC to avoid PostgreSQL timezone issues
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(SimpleProductApiApplication.class, args);
    }

}