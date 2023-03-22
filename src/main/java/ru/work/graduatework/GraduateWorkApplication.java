package ru.work.graduatework;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class GraduateWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraduateWorkApplication.class, args);
    }
}
