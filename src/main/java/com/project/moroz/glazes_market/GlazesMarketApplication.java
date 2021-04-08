package com.project.moroz.glazes_market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GlazesMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlazesMarketApplication.class, args);
    }

}
