package com.bloodlink.camp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.bloodlink")
public class CampServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampServiceApplication.class, args);
    }
}
