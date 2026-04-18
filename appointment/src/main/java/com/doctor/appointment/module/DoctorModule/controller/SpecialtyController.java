package com.doctor.appointment.module.DoctorModule.controller;

import com.doctor.appointment.module.DoctorModule.dto.SpecialtyRequestDTO;
import com.doctor.appointment.module.DoctorModule.dto.SpecialtyResponseDTO;
import com.doctor.appointment.module.DoctorModule.service.SpecialtyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    // ✅ Admin: Create Specialty
    @PostMapping("/admin/specialties")
    public SpecialtyResponseDTO createSpecialty(@RequestBody SpecialtyRequestDTO requestDTO) {
        return specialtyService.createSpecialty(requestDTO);
    }

    // ✅ Patient: Get All Specialties
    @GetMapping("/specialties")
    public List<SpecialtyResponseDTO> getAllSpecialties() {
        return specialtyService.getAllSpecialties();
    }

    // ✅ Get Specialty by ID
    @GetMapping("/specialties/{id}")
    public SpecialtyResponseDTO getSpecialtyById(@PathVariable Long id) {
        return specialtyService.getSpecialtyById(id);
    }
}