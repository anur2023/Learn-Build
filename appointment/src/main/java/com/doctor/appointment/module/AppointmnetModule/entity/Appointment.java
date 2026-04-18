package com.doctor.appointment.module.AppointmnetModule.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK → users (patient)
    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    // FK → doctors
    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    // FK → availability_slots (from Slot Module)
    @Column(name = "slot_id", nullable = false, unique = true)
    private Long slotId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Mode mode; // ONLINE / OFFLINE

    @Column(name = "appointment_time", nullable = false)
    private LocalDateTime appointmentTime;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Appointment() {}

    public Appointment(Long id, Long patientId, Long doctorId, Long slotId,
                       AppointmentStatus status, Mode mode,
                       LocalDateTime appointmentTime, LocalDateTime createdAt) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.slotId = slotId;
        this.status = status;
        this.mode = mode;
        this.appointmentTime = appointmentTime;
        this.createdAt = createdAt;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = AppointmentStatus.SCHEDULED;
        }
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public Long getSlotId() {
        return slotId;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public Mode getMode() {
        return mode;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}