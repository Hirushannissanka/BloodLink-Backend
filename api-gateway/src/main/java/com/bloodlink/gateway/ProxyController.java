package com.bloodlink.gateway;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

@RestController
public class ProxyController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String authUrl;
    private final String requestUrl;
    private final String campUrl;
    private final String notificationUrl;

    public ProxyController(@Value("${app.services.auth-url}") String authUrl,
                           @Value("${app.services.request-url}") String requestUrl,
                           @Value("${app.services.camp-url}") String campUrl,
                           @Value("${app.services.notification-url}") String notificationUrl) {
        this.authUrl = authUrl;
        this.requestUrl = requestUrl;
        this.campUrl = campUrl;
        this.notificationUrl = notificationUrl;
    }

    @RequestMapping({"/api/auth/**", "/api/donors/**", "/patient/**", "/api/requests/**", "/api/camps/**", "/api/notifications/**"})
    public ResponseEntity<byte[]> proxy(HttpServletRequest request, @RequestBody(required = false) byte[] body) throws IOException {
        String path = request.getRequestURI();
        String targetBase = chooseTarget(path) + path;
        java.net.URI uri;
        if (request.getQueryString() != null) {
            uri = org.springframework.web.util.UriComponentsBuilder.fromHttpUrl(targetBase)
                    .query(request.getQueryString())
                    .build(true)
                    .toUri();
        } else {
            uri = org.springframework.web.util.UriComponentsBuilder.fromHttpUrl(targetBase)
                    .build(true)
                    .toUri();
        }

        HttpHeaders headers = new HttpHeaders();
        Collections.list(request.getHeaderNames()).forEach(name -> headers.add(name, request.getHeader(name)));
        headers.remove(HttpHeaders.HOST);

        HttpEntity<byte[]> entity = new HttpEntity<>(body, headers);
        try {
            return restTemplate.exchange(uri, HttpMethod.valueOf(request.getMethod()), entity, byte[].class);
        } catch (org.springframework.web.client.HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsByteArray());
        }
    }

    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> options() {
        return ResponseEntity.noContent().build();
    }

    private String chooseTarget(String path) {
        if (path.startsWith("/api/auth") || path.startsWith("/api/donors") || path.startsWith("/patient")) {
            return authUrl;
        }
        if (path.startsWith("/api/requests")) {
            return requestUrl;
        }
        if (path.startsWith("/api/camps")) {
            return campUrl;
        }
        if (path.startsWith("/api/notifications")) {
            return notificationUrl;
        }
        throw new IllegalArgumentException("No route for path: " + path);
    }
}
