package com.doctor.appointment.module.DoctorModule.entity;

import com.doctor.appointment.module.AuthModule.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // BUG FIX #1: Added @ManyToOne relationship to User.
    // Previously only `userId` (Long) was stored, so SlotService's
    // slot.getDoctor().getUser().getName() caused a NullPointerException.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

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

    public Doctor(Long id, User user, Long specialtyId, Mode mode,
                  Integer experienceYears, Double consultationFee) {
        this.id = id;
        this.user = user;
        this.specialtyId = specialtyId;
        this.mode = mode;
        this.experienceYears = experienceYears;
        this.consultationFee = consultationFee;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    // Convenience getter so existing code using getUserId() still compiles
    public Long getUserId() { return user != null ? user.getId() : null; }

    public Long getSpecialtyId() { return specialtyId; }
    public void setSpecialtyId(Long specialtyId) { this.specialtyId = specialtyId; }

    public Mode getMode() { return mode; }
    public void setMode(Mode mode) { this.mode = mode; }

    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }

    public Double getConsultationFee() { return consultationFee; }
    public void setConsultationFee(Double consultationFee) { this.consultationFee = consultationFee; }
}