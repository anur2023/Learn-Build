package com.doctor.appointment.module.DoctorModule.controller;

import com.doctor.appointment.module.DoctorModule.dto.DoctorRequestDTO;
import com.doctor.appointment.module.DoctorModule.dto.DoctorResponseDTO;
import com.doctor.appointment.module.DoctorModule.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // ✅ Admin: Create Doctor  →  POST /api/admin/doctors
    // BUG FIX #4: Security now protects /api/admin/**, so the path is consistent.
    @PostMapping("/admin/doctors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorResponseDTO> createDoctor(@RequestBody DoctorRequestDTO requestDTO) {
        return new ResponseEntity<>(doctorService.createDoctor(requestDTO), HttpStatus.CREATED);
    }

    // ✅ All authenticated users: Get All Doctors  →  GET /api/doctors
    @GetMapping("/doctors")
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // ✅ All authenticated users: Get Doctor by ID  →  GET /api/doctors/{id}
    @GetMapping("/doctors/{id}")
    public DoctorResponseDTO getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    // ✅ All authenticated users: Filter Doctors  →  GET /api/doctors/filter
    @GetMapping("/doctors/filter")
    public List<DoctorResponseDTO> filterDoctors(
            @RequestParam(required = false) Long specialtyId,
            @RequestParam(required = false) String mode) {
        return doctorService.filterDoctors(specialtyId, mode);
    }
}