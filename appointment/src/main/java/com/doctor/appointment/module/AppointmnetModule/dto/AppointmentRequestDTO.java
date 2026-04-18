package com.doctor.appointment.module.AppointmnetModule.dto;

import com.doctor.appointment.module.AppointmnetModule.entity.Mode;

public class AppointmentRequestDTO {

    private Long patientId;
    private Long doctorId;
    private Long slotId;
    private Mode mode;

    public AppointmentRequestDTO() {}

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
}