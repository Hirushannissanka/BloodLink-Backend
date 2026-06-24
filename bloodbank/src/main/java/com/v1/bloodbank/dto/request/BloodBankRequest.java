package com.v1.bloodbank.dto.request;

//import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodBankRequest {
    private String name;
    private String address;
    private String city;
    private String phoneNumber;
    private String email;
}
