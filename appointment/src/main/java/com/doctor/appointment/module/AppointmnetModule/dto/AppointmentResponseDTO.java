package com.doctor.appointment.module.AppointmnetModule.dto;

import com.doctor.appointment.module.AppointmnetModule.entity.Mode;
import com.doctor.appointment.module.AppointmnetModule.entity.AppointmentStatus;

import java.time.LocalDateTime;

public class AppointmentResponseDTO {

    private Long id;
    private Long patientId;
    private Long doctorId;
    private Long slotId;
    private Mode mode;
    private AppointmentStatus status;
    private LocalDateTime appointmentTime;
    private LocalDateTime createdAt;

    public AppointmentResponseDTO() {}

    public AppointmentResponseDTO(Long id, Long patientId, Long doctorId,
                                  Long slotId, Mode mode, AppointmentStatus status,
                                  LocalDateTime appointmentTime, LocalDateTime createdAt) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.slotId = slotId;
        this.mode = mode;
        this.status = status;
        this.appointmentTime = appointmentTime;
        this.createdAt = createdAt;
    }

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

    public Mode getMode() {
        return mode;
    }

    public AppointmentStatus getStatus() {
        return status;
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

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}