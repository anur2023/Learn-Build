package com.doctor.appointment.module.DoctorModule.dto;

import com.doctor.appointment.module.DoctorModule.entity.Mode;

public class DoctorResponseDTO {

    private Long id;
    private Long userId;
    private Long specialtyId;
    private Mode mode;
    private Integer experienceYears;
    private Double consultationFee;

    public DoctorResponseDTO() {}

    public DoctorResponseDTO(Long id, Long userId, Long specialtyId, Mode mode,
                             Integer experienceYears, Double consultationFee) {
        this.id = id;
        this.userId = userId;
        this.specialtyId = specialtyId;
        this.mode = mode;
        this.experienceYears = experienceYears;
        this.consultationFee = consultationFee;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getSpecialtyId() {
        return specialtyId;
    }

    public Mode getMode() {
        return mode;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public Double getConsultationFee() {
        return consultationFee;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setSpecialtyId(Long specialtyId) {
        this.specialtyId = specialtyId;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public void setConsultationFee(Double consultationFee) {
        this.consultationFee = consultationFee;
    }
}