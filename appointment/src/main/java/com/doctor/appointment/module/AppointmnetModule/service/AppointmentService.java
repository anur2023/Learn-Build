package com.doctor.appointment.module.AppointmnetModule.service;

import com.doctor.appointment.module.AppointmnetModule.dto.AppointmentRequestDTO;
import com.doctor.appointment.module.AppointmnetModule.dto.AppointmentResponseDTO;
import com.doctor.appointment.module.AppointmnetModule.entity.Appointment;
import com.doctor.appointment.module.AppointmnetModule.entity.AppointmentStatus;
import com.doctor.appointment.module.AppointmnetModule.repository.AppointmentRepository;
import com.doctor.appointment.module.SlotModule.entity.AvailabilitySlot;
import com.doctor.appointment.module.SlotModule.repository.AvailabilitySlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    // BUG FIX #2 & #3: Inject SlotRepository to (a) fetch the real
    // appointment time from the slot and (b) mark the slot as booked.
    private final AvailabilitySlotRepository slotRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              AvailabilitySlotRepository slotRepository) {
        this.appointmentRepository = appointmentRepository;
        this.slotRepository = slotRepository;
    }

    // ✅ Book Appointment
    @Transactional
    public AppointmentResponseDTO bookAppointment(AppointmentRequestDTO requestDTO) {

        // Prevent double booking
        appointmentRepository.findBySlotId(requestDTO.getSlotId())
                .ifPresent(a -> {
                    throw new RuntimeException("Slot already booked");
                });

        // BUG FIX #2: Fetch the slot to get the real appointment date/time.
        AvailabilitySlot slot = slotRepository.findById(requestDTO.getSlotId())
                .orElseThrow(() -> new RuntimeException("Slot not found with id: " + requestDTO.getSlotId()));

        if (slot.isBooked()) {
            throw new RuntimeException("Slot already booked");
        }

        // BUG FIX #3: Mark the slot as booked so it cannot be double-booked.
        slot.setBooked(true);
        slotRepository.save(slot);

        Appointment appointment = new Appointment();
        appointment.setPatientId(requestDTO.getPatientId());
        appointment.setDoctorId(requestDTO.getDoctorId());
        appointment.setSlotId(requestDTO.getSlotId());
        appointment.setMode(requestDTO.getMode());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        // BUG FIX #2: Use actual slot date + startTime instead of LocalDateTime.now().
        appointment.setAppointmentTime(
                LocalDateTime.of(slot.getDate(), slot.getStartTime())
        );

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
    @Transactional
    public AppointmentResponseDTO updateStatus(Long appointmentId, String status) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        AppointmentStatus newStatus;
        try {
            newStatus = AppointmentStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }

        // BUG FIX #3 (follow-up): When an appointment is cancelled, free up the slot.
        if (newStatus == AppointmentStatus.CANCELLED) {
            slotRepository.findById(appointment.getSlotId()).ifPresent(slot -> {
                slot.setBooked(false);
                slotRepository.save(slot);
            });
        }

        appointment.setStatus(newStatus);
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