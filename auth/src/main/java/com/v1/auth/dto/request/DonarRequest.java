package com.v1.auth.dto.request;

import com.v1.auth.entity.AvailabilityStatus;
import com.v1.auth.entity.BloodGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonarRequest {
    private Long userId;
    private AvailabilityStatus availabilityStatus;
    private BloodGroup bloodGroup;
    private LocalDate lastDonationDate;

}
