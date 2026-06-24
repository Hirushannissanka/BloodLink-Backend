package com.bloodlink.request.model;

import com.bloodlink.common.model.BloodType;
import com.bloodlink.common.model.RequestStatus;
import com.bloodlink.common.model.UrgencyLevel;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "blood_requests")
public class BloodRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long requesterId;
    private String patientName;
    private String contactPhone;
    private String district;
    private String hospitalName;

    @Enumerated(EnumType.STRING)
    private BloodType bloodTypeNeeded;

    private Integer unitsNeeded;

    @Enumerated(EnumType.STRING)
    private UrgencyLevel urgencyLevel;

    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;

    @Column(length = 1000)
    private String notes;

    private Instant createdAt = Instant.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getRequesterId() { return requesterId; }
    public void setRequesterId(Long requesterId) { this.requesterId = requesterId; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public String getHospitalName() { return hospitalName; }
    public void setHospitalName(String hospitalName) { this.hospitalName = hospitalName; }
    public BloodType getBloodTypeNeeded() { return bloodTypeNeeded; }
    public void setBloodTypeNeeded(BloodType bloodTypeNeeded) { this.bloodTypeNeeded = bloodTypeNeeded; }
    public Integer getUnitsNeeded() { return unitsNeeded; }
    public void setUnitsNeeded(Integer unitsNeeded) { this.unitsNeeded = unitsNeeded; }
    public UrgencyLevel getUrgencyLevel() { return urgencyLevel; }
    public void setUrgencyLevel(UrgencyLevel urgencyLevel) { this.urgencyLevel = urgencyLevel; }
    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
