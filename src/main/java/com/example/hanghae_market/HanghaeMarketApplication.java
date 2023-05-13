package com.example.hanghae_market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HanghaeMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanghaeMarketApplication.class, args);
    }

}
