package com.doctor.appointment.module.SlotModule.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class SlotRequest {

    private Long doctorId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    // ─── No-Args Constructor ───
    public SlotRequest() {}

    // ─── All-Args Constructor ───
    public SlotRequest(Long doctorId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.doctorId = doctorId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // ─── Getters & Setters ───
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
}