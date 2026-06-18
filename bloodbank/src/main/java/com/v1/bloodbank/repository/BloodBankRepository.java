package com.v1.bloodbank.repository;

import com.v1.bloodbank.entity.BloodBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {
    List<BloodBank> findByCity(String city);
}
