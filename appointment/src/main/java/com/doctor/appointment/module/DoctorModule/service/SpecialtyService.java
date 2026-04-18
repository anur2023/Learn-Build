package com.doctor.appointment.module.DoctorModule.service;

import com.doctor.appointment.module.DoctorModule.dto.SpecialtyRequestDTO;
import com.doctor.appointment.module.DoctorModule.dto.SpecialtyResponseDTO;
import com.doctor.appointment.module.DoctorModule.entity.Specialty;
import com.doctor.appointment.module.DoctorModule.repository.SpecialtyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    // ✅ Create Specialty (Admin)
    public SpecialtyResponseDTO createSpecialty(SpecialtyRequestDTO requestDTO) {

        // Prevent duplicate specialty
        specialtyRepository.findByName(requestDTO.getName())
                .ifPresent(s -> {
                    throw new RuntimeException("Specialty already exists");
                });

        Specialty specialty = new Specialty();
        specialty.setName(requestDTO.getName());
        specialty.setDescription(requestDTO.getDescription());

        Specialty saved = specialtyRepository.save(specialty);

        return mapToResponse(saved);
    }

    // ✅ Get All Specialties (Patient)
    public List<SpecialtyResponseDTO> getAllSpecialties() {
        return specialtyRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ Get Specialty by ID
    public SpecialtyResponseDTO getSpecialtyById(Long id) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specialty not found"));

        return mapToResponse(specialty);
    }

    // ✅ Helper
    private SpecialtyResponseDTO mapToResponse(Specialty specialty) {
        return new SpecialtyResponseDTO(
                specialty.getId(),
                specialty.getName(),
                specialty.getDescription()
        );
    }
}