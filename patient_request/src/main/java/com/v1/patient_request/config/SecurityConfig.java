package com.v1.patient_request.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Security Configuration: stateless JWT-based, all /patient/** endpoints require authentication
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // All patient endpoints require authentication
                        .requestMatchers("/patient/**").authenticated()
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session
                        // Stateless — no HttpSession; JWT carries state
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Inject JWT filter before Spring's default auth filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
