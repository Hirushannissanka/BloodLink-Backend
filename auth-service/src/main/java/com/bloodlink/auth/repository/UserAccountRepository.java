package com.bloodlink.auth.repository;

import com.bloodlink.auth.model.UserAccount;
import com.bloodlink.common.model.BloodType;
import com.bloodlink.common.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    List<UserAccount> findByRoleAndBloodTypeAndDistrictIgnoreCase(UserRole role, BloodType bloodType, String district);
    List<UserAccount> findByRoleAndBloodType(UserRole role, BloodType bloodType);
}
