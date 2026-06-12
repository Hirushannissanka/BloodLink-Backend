package com.v1.auth.dto.request;

import com.v1.auth.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
