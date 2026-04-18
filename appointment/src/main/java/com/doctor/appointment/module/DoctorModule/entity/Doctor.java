package com.doctor.appointment.module.DoctorModule.entity;

import com.doctor.appointment.module.DoctorModule.entity.Mode;
import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "specialty_id", nullable = false)
    private Long specialtyId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Mode mode;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "consultation_fee")
    private Double consultationFee;

    public Doctor() {}

    public Doctor(Long id, Long userId, Long specialtyId, Mode mode,
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