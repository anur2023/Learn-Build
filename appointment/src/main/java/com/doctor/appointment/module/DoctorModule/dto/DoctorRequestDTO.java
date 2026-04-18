package com.doctor.appointment.module.DoctorModule.dto;

import com.doctor.appointment.module.DoctorModule.entity.Mode;

public class DoctorRequestDTO {

    private Long userId;
    private Long specialtyId;
    private Mode mode;
    private Integer experienceYears;
    private Double consultationFee;

    public DoctorRequestDTO() {}

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