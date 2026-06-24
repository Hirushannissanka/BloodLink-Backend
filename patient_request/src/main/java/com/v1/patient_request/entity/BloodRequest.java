package com.v1.patient_request.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

// Entity Pattern: maps blood_requests table; owns a ManyToOne to PatientProfile
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blood_requests")
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ManyToOne: one patient can have many blood requests
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientProfile patient;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BloodType bloodTypeNeeded;

    @Column(nullable = false)
    private Integer unitsNeeded;

    @Column(nullable = false)
    private String urgencyLevel; // CRITICAL | URGENT | NORMAL

    @Column(nullable = false)
    private String hospitalName;

    private String hospitalAddress;

    @Column(nullable = false)
    private String city;

    private String district;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
