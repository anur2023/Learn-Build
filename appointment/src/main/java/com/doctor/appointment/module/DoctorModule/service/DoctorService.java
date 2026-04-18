package com.doctor.appointment.module.DoctorModule.service;

import com.doctor.appointment.module.AuthModule.entity.User;
import com.doctor.appointment.module.AuthModule.repository.UserRepository;
import com.doctor.appointment.module.DoctorModule.dto.DoctorRequestDTO;
import com.doctor.appointment.module.DoctorModule.dto.DoctorResponseDTO;
import com.doctor.appointment.module.DoctorModule.entity.Doctor;
import com.doctor.appointment.module.DoctorModule.entity.Mode;
import com.doctor.appointment.module.DoctorModule.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    // BUG FIX #1 (follow-up): Inject UserRepository to resolve User entity
    // from userId when creating a Doctor.
    private final UserRepository userRepository;

    public DoctorService(DoctorRepository doctorRepository, UserRepository userRepository) {
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    // ✅ Create Doctor
    public DoctorResponseDTO createDoctor(DoctorRequestDTO requestDTO) {

        // BUG FIX #1: Look up the User entity so Doctor.user relationship is set properly.
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + requestDTO.getUserId()));

        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setSpecialtyId(requestDTO.getSpecialtyId());
        doctor.setMode(requestDTO.getMode());
        doctor.setExperienceYears(requestDTO.getExperienceYears());
        doctor.setConsultationFee(requestDTO.getConsultationFee());

        Doctor savedDoctor = doctorRepository.save(doctor);
        return mapToResponse(savedDoctor);
    }

    // ✅ Get All Doctors
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ Get Doctor by ID
    public DoctorResponseDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return mapToResponse(doctor);
    }

    // ✅ Filter by Specialty + Mode
    public List<DoctorResponseDTO> filterDoctors(Long specialtyId, String mode) {

        List<Doctor> doctors;
        Mode modeEnum = null;

        if (mode != null) {
            try {
                modeEnum = Mode.valueOf(mode.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid mode value");
            }
        }

        if (specialtyId != null && modeEnum != null) {
            doctors = doctorRepository.findBySpecialtyIdAndMode(specialtyId, modeEnum);
        } else if (specialtyId != null) {
            doctors = doctorRepository.findBySpecialtyId(specialtyId);
        } else if (modeEnum != null) {
            doctors = doctorRepository.findByMode(modeEnum);
        } else {
            doctors = doctorRepository.findAll();
        }

        return doctors.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ Helper: Entity → Response DTO
    private DoctorResponseDTO mapToResponse(Doctor doctor) {
        return new DoctorResponseDTO(
                doctor.getId(),
                doctor.getUserId(),         // convenience getter added to Doctor entity
                doctor.getSpecialtyId(),
                doctor.getMode(),
                doctor.getExperienceYears(),
                doctor.getConsultationFee()
        );
    }
}