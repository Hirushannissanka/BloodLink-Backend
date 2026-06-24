package com.bloodlink.request.config;

import com.bloodlink.common.auth.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {
    @Bean
    JwtService jwtService(@Value("${app.jwt.secret}") String secret,
                          @Value("${app.jwt.expiration-minutes}") long expirationMinutes) {
        return new JwtService(secret, expirationMinutes);
    }

    @Bean
    RestClient restClient() {
        return RestClient.create();
    }
}
