package com.bloodlink.notification.service;

import com.bloodlink.notification.dto.NotificationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final JdbcTemplate jdbcTemplate;
    private final RestClient restClient;
    private final String resendApiKey;

    public NotificationService(JdbcTemplate jdbcTemplate,
                               @Value("${app.resend.api-key:}") String resendApiKey) {
        this.jdbcTemplate = jdbcTemplate;
        this.restClient = RestClient.create();
        this.resendApiKey = resendApiKey;
    }

    public void queue(NotificationRequest request) {
        // Save to DB queue with initial status 'QUEUED'
        jdbcTemplate.update("""
                insert into notifications (donor_id, request_id, email, phone, message, status)
                values (?, ?, ?, ?, ?, 'QUEUED')
                """, request.donorId(), request.requestId(), request.email(), request.phone(), request.message());

        // Send email using Resend API
        if (request.email() != null && !request.email().isBlank() && resendApiKey != null && !resendApiKey.isBlank()) {
            try {
                Map<String, Object> body = Map.of(
                        "from", "onboarding@resend.dev",
                        "to", request.email(),
                        "subject", "BloodLink - Urgent Blood Donation Request Match",
                        "html", "<strong>" + request.message() + "</strong>"
                );

                restClient.post()
                        .uri("https://api.resend.com/emails")
                        .header("Authorization", "Bearer " + resendApiKey)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(body)
                        .retrieve()
                        .toBodilessEntity();

                log.info("Successfully sent notification email to {}", request.email());

                // Update status to SENT
                jdbcTemplate.update("""
                        update notifications 
                        set status = 'SENT' 
                        where donor_id = ? and request_id = ? and email = ?
                        """, request.donorId(), request.requestId(), request.email());

            } catch (Exception e) {
                log.error("Failed to send email to {}: {}", request.email(), e.getMessage());
                // Update status to FAILED
                jdbcTemplate.update("""
                        update notifications 
                        set status = 'FAILED' 
                        where donor_id = ? and request_id = ? and email = ?
                        """, request.donorId(), request.requestId(), request.email());
            }
        } else {
            log.warn("Skipping email notification: either email is blank or Resend API key is not configured.");
        }
    }
}
