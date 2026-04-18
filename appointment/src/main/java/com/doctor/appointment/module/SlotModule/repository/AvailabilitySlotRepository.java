package com.doctor.appointment.module.SlotModule.repository;

import com.doctor.appointment.module.SlotModule.entity.AvailabilitySlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot, Long> {

    // Get all slots for a doctor on a specific date
    List<AvailabilitySlot> findByDoctorIdAndDate(Long doctorId, LocalDate date);

    // Get only available (not booked) slots for a doctor on a date
    List<AvailabilitySlot> findByDoctorIdAndDateAndIsBooked(Long doctorId, LocalDate date, boolean isBooked);

    // Check if a slot already exists at the same time (prevent duplicate slots)
    boolean existsByDoctorIdAndDateAndStartTime(Long doctorId, LocalDate date, LocalTime startTime);

    // Get all slots for a doctor
    List<AvailabilitySlot> findByDoctorId(Long doctorId);
}