package com.bloodlink.camp.service;

import com.bloodlink.camp.dto.CampRequest;
import com.bloodlink.camp.dto.CampResponse;
import com.bloodlink.camp.model.DonationCamp;
import com.bloodlink.camp.repository.DonationCampRepository;
import com.bloodlink.common.auth.JwtClaims;
import com.bloodlink.common.auth.JwtService;
import com.bloodlink.common.model.UserRole;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Service
public class CampService {
    private final DonationCampRepository camps;
    private final JwtService jwtService;

    public CampService(DonationCampRepository camps, JwtService jwtService) {
        this.camps = camps;
        this.jwtService = jwtService;
    }

    public List<CampResponse> list(String district, boolean upcoming) {
        List<DonationCamp> result;
        if (district != null && !district.isBlank() && upcoming) {
            result = camps.findByDistrictIgnoreCaseAndDateGreaterThanEqualOrderByDateAsc(district, LocalDate.now());
        } else if (district != null && !district.isBlank()) {
            result = camps.findByDistrictIgnoreCaseOrderByDateAsc(district);
        } else if (upcoming) {
            result = camps.findByDateGreaterThanEqualOrderByDateAsc(LocalDate.now());
        } else {
            result = camps.findAllByOrderByDateAsc();
        }
        return result.stream().map(this::response).toList();
    }

    public CampResponse create(String authorization, CampRequest request) {
        JwtClaims claims = jwtService.parse(authorization);
        if (claims.role() != UserRole.HOSPITAL && claims.role() != UserRole.ADMIN) {
            throw new IllegalArgumentException("Only hospitals can create donation camps.");
        }
        DonationCamp camp = new DonationCamp();
        camp.setOrganizerId(claims.userId());
        camp.setName(request.name());
        camp.setOrganizer(request.organizer());
        camp.setDistrict(request.district());
        camp.setLocation(request.location());
        camp.setDate(request.date());
        camp.setStartTime(request.startTime());
        camp.setEndTime(request.endTime());
        camp.setContactPhone(request.contactPhone());
        camp.setContactEmail(request.contactEmail());
        camp.setNotes(request.notes());
        camp.setTargetUnits(request.targetUnits());
        camp.setBloodTypesNeeded(joinBloodTypes(request.bloodTypesNeeded()));
        return response(camps.save(camp));
    }

    private String joinBloodTypes(List<String> bloodTypes) {
        if (bloodTypes == null || bloodTypes.isEmpty()) {
            return "";
        }
        StringJoiner joiner = new StringJoiner(",");
        bloodTypes.forEach(joiner::add);
        return joiner.toString();
    }

    private CampResponse response(DonationCamp camp) {
        List<String> bloodTypes = camp.getBloodTypesNeeded() == null || camp.getBloodTypesNeeded().isBlank()
                ? List.of()
                : Arrays.asList(camp.getBloodTypesNeeded().split(","));
        return new CampResponse(camp.getId(), camp.getName(), camp.getOrganizer(), camp.getDistrict(),
                camp.getLocation(), camp.getDate(), camp.getStartTime(), camp.getEndTime(), camp.getContactPhone(),
                camp.getContactEmail(), camp.getNotes(), camp.getTargetUnits(), camp.getRegisteredDonors(), bloodTypes);
    }
}
