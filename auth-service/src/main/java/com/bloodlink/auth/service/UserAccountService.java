package com.bloodlink.auth.service;

import com.bloodlink.auth.dto.AuthResponse;
import com.bloodlink.auth.dto.LoginRequest;
import com.bloodlink.auth.dto.PatientProfileRequest;
import com.bloodlink.auth.dto.RegisterRequest;
import com.bloodlink.auth.model.PatientProfile;
import com.bloodlink.auth.model.UserAccount;
import com.bloodlink.auth.repository.PatientProfileRepository;
import com.bloodlink.auth.repository.UserAccountRepository;
import com.bloodlink.common.auth.JwtClaims;
import com.bloodlink.common.auth.JwtService;
import com.bloodlink.common.dto.UserSummary;
import com.bloodlink.common.model.BloodType;
import com.bloodlink.common.model.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserAccountService {
    private final UserAccountRepository users;
    private final PatientProfileRepository patientProfiles;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserAccountService(UserAccountRepository users, PatientProfileRepository patientProfiles,
                              PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.users = users;
        this.patientProfiles = patientProfiles;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (users.existsByEmailIgnoreCase(request.email())) {
            throw new IllegalArgumentException("Email is already registered.");
        }
        UserAccount user = new UserAccount();
        user.setFullName(request.fullName());
        user.setEmail(request.email().trim().toLowerCase());
        user.setPhone(request.phone());
        user.setAge(request.age());
        user.setDistrict(request.district());
        user.setNearestHospital(request.nearestHospital());
        user.setBloodType(BloodType.fromClient(request.bloodType()));
        user.setRole(request.role());
        user.setEmergencyContact(request.emergencyContact());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        UserAccount saved = users.save(user);
        return authResponse(saved);
    }

    public AuthResponse login(LoginRequest request) {
        UserAccount user = users.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }
        return authResponse(user);
    }

    public UserSummary currentUser(String authorization) {
        JwtClaims claims = jwtService.parse(authorization);
        return users.findById(claims.userId()).map(this::summary)
                .orElseThrow(() -> new IllegalArgumentException("User was not found."));
    }

    @Transactional
    public PatientProfile createPatientProfile(String authorization, PatientProfileRequest request) {
        JwtClaims claims = jwtService.parse(authorization);
        PatientProfile profile = patientProfiles.findByUserId(claims.userId()).orElseGet(PatientProfile::new);
        profile.setUserId(claims.userId());
        profile.setBloodType(BloodType.fromClient(request.bloodType()));
        profile.setCity(request.city());
        profile.setDistrict(request.district());
        profile.setEmergencyContact(request.emergencyContact());
        profile.setMedicalNotes(request.medicalNotes());
        return patientProfiles.save(profile);
    }

    public UserSummary donorProfile(String authorization) {
        JwtClaims claims = jwtService.parse(authorization);
        UserAccount user = users.findById(claims.userId())
                .orElseThrow(() -> new IllegalArgumentException("User was not found."));
        if (user.getRole() != UserRole.DONOR) {
            throw new IllegalArgumentException("Only donors can access this profile.");
        }
        return summary(user);
    }

    @Transactional
    public UserSummary updateDonorProfile(String authorization, RegisterRequest request) {
        JwtClaims claims = jwtService.parse(authorization);
        UserAccount user = users.findById(claims.userId())
                .orElseThrow(() -> new IllegalArgumentException("User was not found."));
        user.setFullName(request.fullName());
        user.setPhone(request.phone());
        user.setAge(request.age());
        user.setDistrict(request.district());
        user.setNearestHospital(request.nearestHospital());
        user.setBloodType(BloodType.fromClient(request.bloodType()));
        return summary(users.save(user));
    }

    @Transactional
    public void addRewardPoints(Long donorId, int points) {
        UserAccount donor = users.findById(donorId).orElseThrow();
        donor.setRewardPoints(donor.getRewardPoints() + points);
        users.save(donor);
    }

    public List<UserSummary> findMatchingDonors(String bloodType, String district) {
        List<UserAccount> match;
        if (district == null || district.trim().isEmpty()) {
            match = users.findByRoleAndBloodType(UserRole.DONOR, BloodType.fromClient(bloodType));
        } else {
            match = users.findByRoleAndBloodTypeAndDistrictIgnoreCase(UserRole.DONOR, BloodType.fromClient(bloodType), district);
        }
        return match.stream()
                .map(this::summary)
                .toList();
    }

    private AuthResponse authResponse(UserAccount user) {
        return new AuthResponse(jwtService.createToken(user.getId(), user.getEmail(), user.getRole()), summary(user));
    }

    private UserSummary summary(UserAccount user) {
        String bloodType = user.getBloodType() == null ? null : user.getBloodType().toClient();
        return new UserSummary(user.getId(), user.getFullName(), user.getEmail(), user.getPhone(), user.getAge(),
                user.getDistrict(), user.getNearestHospital(), bloodType, user.getRole(), user.getRewardPoints());
    }
}
