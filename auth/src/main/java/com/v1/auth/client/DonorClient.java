package com.v1.auth.client;

import com.v1.auth.dto.request.DonarRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DonorClient{
    private final WebClient webClient;

    public DonorClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8082/donar").build();

    }

    public void sendDonorDetails(DonarRequest donarRequest) {
        //System.out.println(donarRequest);
        webClient.post()
                .uri("/add") // endpoint in Donor Service
                .bodyValue(donarRequest)
                .retrieve()
                .bodyToMono(Void.class)
                .block(); // synchronous call; use reactive in production
    }
}
