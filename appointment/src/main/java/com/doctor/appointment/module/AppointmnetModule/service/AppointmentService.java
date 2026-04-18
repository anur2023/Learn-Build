package com.doctor.appointment.module.AppointmnetModule.service;



import com.doctor.appointment.module.AppointmnetModule.entity.Mode;
import com.doctor.appointment.module.AppointmnetModule.dto.AppointmentRequestDTO;
import com.doctor.appointment.module.AppointmnetModule.dto.AppointmentResponseDTO;
import com.doctor.appointment.module.AppointmnetModule.entity.Appointment;
import com.doctor.appointment.module.AppointmnetModule.entity.AppointmentStatus;
import com.doctor.appointment.module.AppointmnetModule.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // ✅ Book Appointment
    public AppointmentResponseDTO bookAppointment(AppointmentRequestDTO requestDTO) {

        // 🔴 Prevent double booking
        appointmentRepository.findBySlotId(requestDTO.getSlotId())
                .ifPresent(a -> {
                    throw new RuntimeException("Slot already booked");
                });

        Appointment appointment = new Appointment();
        appointment.setPatientId(requestDTO.getPatientId());
        appointment.setDoctorId(requestDTO.getDoctorId());
        appointment.setSlotId(requestDTO.getSlotId());
        appointment.setMode(requestDTO.getMode());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setAppointmentTime(LocalDateTime.now()); // can be improved later

        Appointment saved = appointmentRepository.save(appointment);

        return mapToResponse(saved);
    }

    // ✅ Get all appointments (Admin)
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ Get patient appointment history
    public List<AppointmentResponseDTO> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ Get doctor appointments
    public List<AppointmentResponseDTO> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ Update appointment status (Doctor/Admin)
    public AppointmentResponseDTO updateStatus(Long appointmentId, String status) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        try {
            appointment.setStatus(AppointmentStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status");
        }

        Appointment updated = appointmentRepository.save(appointment);

        return mapToResponse(updated);
    }

    // ✅ Helper: Entity → DTO
    private AppointmentResponseDTO mapToResponse(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getSlotId(),
                appointment.getMode(),
                appointment.getStatus(),
                appointment.getAppointmentTime(),
                appointment.getCreatedAt()
        );
    }
}