package com.doctor.appointment.module.AppointmnetModule.service;
import com.doctor.appointment.module.DoctorModule.entity.Doctor;
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
    private final AvailabilitySlotRepository slotRepository;
    private final com.doctor.appointment.module.DoctorModule.repository.DoctorRepository doctorRepository; // ADD THIS

    public AppointmentService(AppointmentRepository appointmentRepository,
                              AvailabilitySlotRepository slotRepository,
                              com.doctor.appointment.module.DoctorModule.repository.DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.slotRepository = slotRepository;
        this.doctorRepository = doctorRepository; // ADD THIS
    }


    @Transactional
    public AppointmentResponseDTO bookAppointment(AppointmentRequestDTO requestDTO) {

        // Prevent double booking
        appointmentRepository.findBySlotId(requestDTO.getSlotId())
                .ifPresent(a -> {
                    throw new RuntimeException("Slot already booked");
                });

        // Fetch the slot
        AvailabilitySlot slot = slotRepository.findById(requestDTO.getSlotId())
                .orElseThrow(() -> new RuntimeException("Slot not found with id: " + requestDTO.getSlotId()));

        if (slot.isBooked()) {
            throw new RuntimeException("Slot already booked");
        }

        // ✅ NEW: Fetch the doctor and validate mode compatibility
        Doctor doctor = doctorRepository.findById(requestDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + requestDTO.getDoctorId()));

        // Convert DoctorModule.Mode to AppointmentModule.Mode for comparison
        com.doctor.appointment.module.AppointmnetModule.entity.Mode requestedMode = requestDTO.getMode();
        String doctorMode = doctor.getMode().name(); // "ONLINE" or "OFFLINE"

        if (!doctorMode.equals(requestedMode.name())) {
            throw new RuntimeException(
                    "Doctor only accepts " + doctorMode + " appointments. Cannot book as " + requestedMode.name()
            );
        }

        // Mark slot as booked
        slot.setBooked(true);
        slotRepository.save(slot);

        Appointment appointment = new Appointment();
        appointment.setPatientId(requestDTO.getPatientId());
        appointment.setDoctorId(requestDTO.getDoctorId());
        appointment.setSlotId(requestDTO.getSlotId());
        appointment.setMode(requestDTO.getMode());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
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