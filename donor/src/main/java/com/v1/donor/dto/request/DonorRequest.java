package com.v1.donor.dto.request;

import com.v1.donor.entity.AvailabilityStatus;
import com.v1.donor.entity.BloodGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DonorRequest {
    private Long donorId;
    private String userId;
    private BloodGroup bloodGroup;
    private AvailabilityStatus availabilityStatus;
    private LocalDate lastDonationDate;
}
