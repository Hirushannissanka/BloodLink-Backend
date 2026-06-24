package com.v1.donor.dto.response;

import com.v1.donor.entity.AvailabilityStatus;
import com.v1.donor.entity.BloodGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonorResponse {
    //private String userName;
    private String userId;
    private String donorId;
    //private String email;
    //private String phone;
    private AvailabilityStatus availabilityStatus;
    private LocalDate lastDonationDate;
    private BloodGroup bloodGroup;
}
