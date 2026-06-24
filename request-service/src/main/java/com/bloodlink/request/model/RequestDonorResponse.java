package com.bloodlink.request.model;

import com.bloodlink.common.model.DonorResponseStatus;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "request_donor_responses")
public class RequestDonorResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long requestId;
    private Long donorId;

    @Enumerated(EnumType.STRING)
    private DonorResponseStatus status;

    private Instant respondedAt = Instant.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getRequestId() { return requestId; }
    public void setRequestId(Long requestId) { this.requestId = requestId; }
    public Long getDonorId() { return donorId; }
    public void setDonorId(Long donorId) { this.donorId = donorId; }
    public DonorResponseStatus getStatus() { return status; }
    public void setStatus(DonorResponseStatus status) { this.status = status; }
    public Instant getRespondedAt() { return respondedAt; }
    public void setRespondedAt(Instant respondedAt) { this.respondedAt = respondedAt; }
}
