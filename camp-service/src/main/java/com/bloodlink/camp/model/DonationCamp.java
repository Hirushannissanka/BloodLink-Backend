package com.bloodlink.camp.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "donation_camps")
public class DonationCamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long organizerId;
    private String name;
    private String organizer;
    private String district;
    private String location;
    private LocalDate date;
    private String startTime;
    private String endTime;
    private String contactPhone;
    private String contactEmail;
    private String notes;
    private Integer targetUnits;
    private Integer registeredDonors = 0;
    private String bloodTypesNeeded;
    private Instant createdAt = Instant.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOrganizerId() { return organizerId; }
    public void setOrganizerId(Long organizerId) { this.organizerId = organizerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getOrganizer() { return organizer; }
    public void setOrganizer(String organizer) { this.organizer = organizer; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Integer getTargetUnits() { return targetUnits; }
    public void setTargetUnits(Integer targetUnits) { this.targetUnits = targetUnits; }
    public Integer getRegisteredDonors() { return registeredDonors; }
    public void setRegisteredDonors(Integer registeredDonors) { this.registeredDonors = registeredDonors; }
    public String getBloodTypesNeeded() { return bloodTypesNeeded; }
    public void setBloodTypesNeeded(String bloodTypesNeeded) { this.bloodTypesNeeded = bloodTypesNeeded; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
