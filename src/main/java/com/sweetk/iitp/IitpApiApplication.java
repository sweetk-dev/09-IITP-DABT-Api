package com.sweetk.iitp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IitpApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(IitpApiApplication.class, args);
    }
} 