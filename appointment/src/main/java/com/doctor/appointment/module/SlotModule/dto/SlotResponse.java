package com.doctor.appointment.module.SlotModule.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class SlotResponse {

    private Long id;
    private Long doctorId;
    private String doctorName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isBooked;

    // ─── No-Args Constructor ───
    public SlotResponse() {}

    // ─── All-Args Constructor ───
    public SlotResponse(Long id, Long doctorId, String doctorName,
                        LocalDate date, LocalTime startTime,
                        LocalTime endTime, boolean isBooked) {
        this.id = id;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isBooked = isBooked;
    }

    // ─── Getters & Setters ───
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public boolean isBooked() { return isBooked; }
    public void setBooked(boolean booked) { isBooked = booked; }
}