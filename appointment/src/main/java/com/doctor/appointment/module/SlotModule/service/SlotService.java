package com.doctor.appointment.module.SlotModule.service;

import com.doctor.appointment.module.DoctorModule.entity.Doctor;
import com.doctor.appointment.module.DoctorModule.repository.DoctorRepository;
import com.doctor.appointment.module.SlotModule.dto.SlotRequest;
import com.doctor.appointment.module.SlotModule.dto.SlotResponse;
import com.doctor.appointment.module.SlotModule.entity.AvailabilitySlot;
import com.doctor.appointment.module.SlotModule.repository.AvailabilitySlotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SlotService {

    private final AvailabilitySlotRepository slotRepository;
    private final DoctorRepository doctorRepository;

    public SlotService(AvailabilitySlotRepository slotRepository,
                       DoctorRepository doctorRepository) {
        this.slotRepository = slotRepository;
        this.doctorRepository = doctorRepository;
    }

    // ─── Create Slot ───
    public SlotResponse createSlot(SlotRequest request) {

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + request.getDoctorId()));

        // Prevent duplicate slot for same doctor, date, startTime
        boolean exists = slotRepository.existsByDoctorIdAndDateAndStartTime(
                request.getDoctorId(), request.getDate(), request.getStartTime());

        if (exists) {
            throw new RuntimeException("Slot already exists for this doctor at the given date and time");
        }

        // Validate startTime is before endTime
        if (!request.getStartTime().isBefore(request.getEndTime())) {
            throw new RuntimeException("Start time must be before end time");
        }

        // Validate date is not in the past
        if (request.getDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot create slot for a past date");
        }

        AvailabilitySlot slot = new AvailabilitySlot();
        slot.setDoctor(doctor);
        slot.setDate(request.getDate());
        slot.setStartTime(request.getStartTime());
        slot.setEndTime(request.getEndTime());
        slot.setBooked(false);

        AvailabilitySlot saved = slotRepository.save(slot);
        return mapToResponse(saved);
    }

    // ─── Get Slots by Doctor and Date ───
    public List<SlotResponse> getSlotsByDoctorAndDate(Long doctorId, LocalDate date) {

        doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        return slotRepository.findByDoctorIdAndDate(doctorId, date)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ─── Get Available (not booked) Slots by Doctor and Date ───
    public List<SlotResponse> getAvailableSlots(Long doctorId, LocalDate date) {

        doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        return slotRepository.findByDoctorIdAndDateAndIsBooked(doctorId, date, false)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ─── Get All Slots by Doctor ───
    public List<SlotResponse> getAllSlotsByDoctor(Long doctorId) {

        doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        return slotRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ─── Delete Slot ───
    public void deleteSlot(Long slotId) {

        AvailabilitySlot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found with id: " + slotId));

        if (slot.isBooked()) {
            throw new RuntimeException("Cannot delete a slot that is already booked");
        }

        slotRepository.deleteById(slotId);
    }

    // ─── Map Entity to Response ───
    private SlotResponse mapToResponse(AvailabilitySlot slot) {
        return new SlotResponse(
                slot.getId(),
                slot.getDoctor().getId(),
                slot.getDoctor().getUser().getName(),
                slot.getDate(),
                slot.getStartTime(),
                slot.getEndTime(),
                slot.isBooked()
        );
    }
}