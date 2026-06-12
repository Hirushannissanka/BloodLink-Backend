package com.v1.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="user")
public class User {
 @Id
 @GeneratedValue( strategy = GenerationType.IDENTITY)
 private Long userId;
 private String userName;
 private String password;
 private String email;
 private String phone;
 private String address;
 @Enumerated(EnumType.STRING)
 private UserRole role;
}
