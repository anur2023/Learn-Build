package com.doctor.appointment.module.DoctorModule.repository;

import com.doctor.appointment.module.DoctorModule.entity.Doctor;
import com.doctor.appointment.module.DoctorModule.entity.Mode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Find doctor by userId (since 1:1 mapping with users)
    Optional<Doctor> findByUserId(Long userId);

    // Filter doctors by specialty
    List<Doctor> findBySpecialtyId(Long specialtyId);

    // Filter doctors by mode (ONLINE / OFFLINE)
    List<Doctor> findByMode(Mode mode);

    // Filter by specialty + mode (important for your use case)
    List<Doctor> findBySpecialtyIdAndMode(Long specialtyId, Mode mode);
}