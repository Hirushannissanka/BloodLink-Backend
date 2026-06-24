package com.bloodlink.camp.dto;

import java.time.LocalDate;
import java.util.List;

public record CampResponse(
        Long id,
        String name,
        String organizer,
        String district,
        String location,
        LocalDate date,
        String startTime,
        String endTime,
        String contactPhone,
        String contactEmail,
        String notes,
        Integer targetUnits,
        Integer registeredDonors,
        List<String> bloodTypesNeeded
) {
}
