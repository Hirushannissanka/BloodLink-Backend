package com.v1.auth.dto.request;

import com.v1.auth.entity.AvailabilityStatus;
import com.v1.auth.entity.BloodGroup;
import com.v1.auth.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String address;
    private UserRole role;

    //donar atributes
    private BloodGroup bloodGroup;
    private AvailabilityStatus availabilityStatus;
    private LocalDate lastDonationDate;
}
