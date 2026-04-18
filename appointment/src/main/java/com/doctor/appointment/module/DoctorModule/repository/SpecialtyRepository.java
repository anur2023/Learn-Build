package com.doctor.appointment.module.DoctorModule.repository;

import com.doctor.appointment.module.DoctorModule.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

    // Find by name (useful for validation or avoiding duplicates)
    Optional<Specialty> findByName(String name);
}