package com.doctor.appointment.module.AppointmnetModule.conroller;

import com.doctor.appointment.module.AppointmnetModule.dto.AppointmentRequestDTO;
import com.doctor.appointment.module.AppointmnetModule.dto.AppointmentResponseDTO;
import com.doctor.appointment.module.AppointmnetModule.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // ✅ Patient: Book Appointment
    @PostMapping("/appointments")
    public AppointmentResponseDTO bookAppointment(@RequestBody AppointmentRequestDTO requestDTO) {
        return appointmentService.bookAppointment(requestDTO);
    }

    // ✅ Admin: Get all appointments
    @GetMapping("/admin/appointments")
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // ✅ Patient: View own appointments
    @GetMapping("/appointments/patient/{patientId}")
    public List<AppointmentResponseDTO> getByPatient(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsByPatient(patientId);
    }

    // ✅ Doctor: View own appointments
    @GetMapping("/appointments/doctor/{doctorId}")
    public List<AppointmentResponseDTO> getByDoctor(@PathVariable Long doctorId) {
        return appointmentService.getAppointmentsByDoctor(doctorId);
    }

    // ✅ Doctor/Admin: Update status
    @PutMapping("/appointments/{id}/status")
    public AppointmentResponseDTO updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        return appointmentService.updateStatus(id, status);
    }
}