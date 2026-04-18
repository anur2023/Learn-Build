package com.doctor.appointment.module.DoctorModule.controller;

import com.doctor.appointment.module.DoctorModule.dto.SpecialtyRequestDTO;
import com.doctor.appointment.module.DoctorModule.dto.SpecialtyResponseDTO;
import com.doctor.appointment.module.DoctorModule.service.SpecialtyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    // ✅ Admin: Create Specialty  →  POST /api/admin/specialties
    // BUG FIX #4: Security now covers /api/admin/**, path is consistent.
    @PostMapping("/admin/specialties")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpecialtyResponseDTO> createSpecialty(
            @RequestBody SpecialtyRequestDTO requestDTO) {
        return new ResponseEntity<>(specialtyService.createSpecialty(requestDTO), HttpStatus.CREATED);
    }

    // ✅ All authenticated users: Get All Specialties  →  GET /api/specialties
    @GetMapping("/specialties")
    public List<SpecialtyResponseDTO> getAllSpecialties() {
        return specialtyService.getAllSpecialties();
    }

    // ✅ All authenticated users: Get Specialty by ID  →  GET /api/specialties/{id}
    @GetMapping("/specialties/{id}")
    public SpecialtyResponseDTO getSpecialtyById(@PathVariable Long id) {
        return specialtyService.getSpecialtyById(id);
    }
}