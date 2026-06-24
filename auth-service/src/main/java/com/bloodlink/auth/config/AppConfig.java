package com.bloodlink.auth.config;

import com.bloodlink.common.auth.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtService jwtService(@Value("${app.jwt.secret}") String secret,
                          @Value("${app.jwt.expiration-minutes}") long expirationMinutes) {
        return new JwtService(secret, expirationMinutes);
    }
}
