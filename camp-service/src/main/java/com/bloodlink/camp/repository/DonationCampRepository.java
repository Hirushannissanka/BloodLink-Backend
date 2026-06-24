package com.bloodlink.camp.repository;

import com.bloodlink.camp.model.DonationCamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DonationCampRepository extends JpaRepository<DonationCamp, Long> {
    List<DonationCamp> findByDistrictIgnoreCaseOrderByDateAsc(String district);
    List<DonationCamp> findByDateGreaterThanEqualOrderByDateAsc(LocalDate date);
    List<DonationCamp> findByDistrictIgnoreCaseAndDateGreaterThanEqualOrderByDateAsc(String district, LocalDate date);
    List<DonationCamp> findAllByOrderByDateAsc();
}
