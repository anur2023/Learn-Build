package com.doctor.appointment.module.AppointmnetModule.conroller;

import com.doctor.appointment.module.AppointmnetModule.dto.AppointmentRequestDTO;
import com.doctor.appointment.module.AppointmnetModule.dto.AppointmentResponseDTO;
import com.doctor.appointment.module.AppointmnetModule.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // ✅ Patient: Book Appointment  →  POST /api/appointments
    @PostMapping("/appointments")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<AppointmentResponseDTO> bookAppointment(
            @RequestBody AppointmentRequestDTO requestDTO) {
        return new ResponseEntity<>(appointmentService.bookAppointment(requestDTO), HttpStatus.CREATED);
    }

    // ✅ Admin: Get all appointments  →  GET /api/admin/appointments
    // BUG FIX #4: Was mapped to /admin/appointments inside an /api controller,
    // making the effective path /api/admin/appointments. SecurityConfig now
    // correctly protects /api/admin/**, so this is consistent.
    @GetMapping("/admin/appointments")
    @PreAuthorize("hasRole('ADMIN')")
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // ✅ Patient: View own appointments  →  GET /api/appointments/patient/{patientId}
    @GetMapping("/appointments/patient/{patientId}")
    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN')")
    public List<AppointmentResponseDTO> getByPatient(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsByPatient(patientId);
    }

    // ✅ Doctor: View own appointments  →  GET /api/appointments/doctor/{doctorId}
    @GetMapping("/appointments/doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public List<AppointmentResponseDTO> getByDoctor(@PathVariable Long doctorId) {
        return appointmentService.getAppointmentsByDoctor(doctorId);
    }

    // ✅ Doctor/Admin: Update status  →  PUT /api/appointments/{id}/status
    @PutMapping("/appointments/{id}/status")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public AppointmentResponseDTO updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return appointmentService.updateStatus(id, status);
    }
}