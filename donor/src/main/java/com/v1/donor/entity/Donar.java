package com.v1.donor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="donar")
public class Donar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DonarId;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;
    private LocalDate lastDonationDate;


}
