package com.exalt.sampleproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.Entity;

@SpringBootApplication
@EnableJpaAuditing
public class SampleProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleProjectApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {
            System.out.println("Let’s inspect the beans provided by Spring Boot:");

            String[] beanNames = context.getBeanDefinitionNames();
            for (String bean: beanNames) {
                System.out.println(bean);
            }
        };
    }
}