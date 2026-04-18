package com.doctor.appointment.module.AppointmnetModule.repository;

import com.doctor.appointment.module.AppointmnetModule.entity.Appointment;
import com.doctor.appointment.module.AppointmnetModule.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Prevent double booking (slot should be used once)
    Optional<Appointment> findBySlotId(Long slotId);

    // Get all appointments of a patient
    List<Appointment> findByPatientId(Long patientId);

    // Get all appointments of a doctor
    List<Appointment> findByDoctorId(Long doctorId);

    // Filter by status
    List<Appointment> findByStatus(AppointmentStatus status);

    // Doctor + status
    List<Appointment> findByDoctorIdAndStatus(Long doctorId, AppointmentStatus status);

    // Patient + status
    List<Appointment> findByPatientIdAndStatus(Long patientId, AppointmentStatus status);
}