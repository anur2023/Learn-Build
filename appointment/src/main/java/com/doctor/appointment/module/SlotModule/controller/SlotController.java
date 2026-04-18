package com.doctor.appointment.module.SlotModule.controller;

import com.doctor.appointment.module.SlotModule.dto.SlotRequest;
import com.doctor.appointment.module.SlotModule.dto.SlotResponse;
import com.doctor.appointment.module.SlotModule.service.SlotService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/slots")
public class SlotController {

    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    // POST /slots  → Doctor/Admin creates a slot
    @PostMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<SlotResponse> createSlot(@RequestBody SlotRequest request) {
        return new ResponseEntity<>(slotService.createSlot(request), HttpStatus.CREATED);
    }

    // GET /slots?doctorId=1&date=2025-06-01  → All slots for a doctor on a date
    @GetMapping
    public ResponseEntity<List<SlotResponse>> getSlotsByDoctorAndDate(
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(slotService.getSlotsByDoctorAndDate(doctorId, date));
    }

    // GET /slots/available?doctorId=1&date=2025-06-01  → Only available slots
    @GetMapping("/available")
    public ResponseEntity<List<SlotResponse>> getAvailableSlots(
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(slotService.getAvailableSlots(doctorId, date));
    }

    // GET /slots/doctor/{doctorId}  → All slots for a doctor
    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<List<SlotResponse>> getAllSlotsByDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(slotService.getAllSlotsByDoctor(doctorId));
    }

    // DELETE /slots/{id}  → Doctor/Admin deletes a slot
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<String> deleteSlot(@PathVariable Long id) {
        slotService.deleteSlot(id);
        return ResponseEntity.ok("Slot deleted successfully");
    }
}