package com.doctor.appointment.module.DoctorModule.controller;

import com.doctor.appointment.module.DoctorModule.dto.DoctorRequestDTO;
import com.doctor.appointment.module.DoctorModule.dto.DoctorResponseDTO;
import com.doctor.appointment.module.DoctorModule.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // ✅ Admin: Create Doctor
    @PostMapping("/admin/doctors")
    public DoctorResponseDTO createDoctor(@RequestBody DoctorRequestDTO requestDTO) {
        return doctorService.createDoctor(requestDTO);
    }

    // ✅ Patient: Get All Doctors
    @GetMapping("/doctors")
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // ✅ Patient: Get Doctor by ID
    @GetMapping("/doctors/{id}")
    public DoctorResponseDTO getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    // ✅ Patient: Filter Doctors
    @GetMapping("/doctors/filter")
    public List<DoctorResponseDTO> filterDoctors(
            @RequestParam(required = false) Long specialtyId,
            @RequestParam(required = false) String mode
    ) {
        return doctorService.filterDoctors(specialtyId, mode);
    }
}