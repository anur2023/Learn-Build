package com.doctor.appointment.module.DoctorModule.repository;

import com.doctor.appointment.module.DoctorModule.entity.Doctor;
import com.doctor.appointment.module.DoctorModule.entity.Mode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // BUG FIX #1 (follow-up): After Doctor.user changed from Long userId to a
    // @ManyToOne User, Spring Data derives the query from the field path.
    // "findByUserId" must now be "findByUser_Id" to traverse the relationship.
    Optional<Doctor> findByUser_Id(Long userId);

    List<Doctor> findBySpecialtyId(Long specialtyId);

    List<Doctor> findByMode(Mode mode);

    List<Doctor> findBySpecialtyIdAndMode(Long specialtyId, Mode mode);
}