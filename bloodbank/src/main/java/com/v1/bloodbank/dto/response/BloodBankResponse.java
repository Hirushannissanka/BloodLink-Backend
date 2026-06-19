package com.v1.bloodbank.dto.response;

//import com.v1.bloodbank.entity.BloodGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodBankResponse {
    private Long bloodBankId;
    private String name;
    private String address;
    private String city;
    private String phoneNumber;
    private String email;
}
