package com.doctor.appointment.module.DoctorModule.dto;

public class SpecialtyRequestDTO {

    private String name;
    private String description;

    public SpecialtyRequestDTO() {}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}