import axiosInstance from "../api/axios";

export const bookAppointment = (data) =>
  axiosInstance.post("/api/appointments", data);

export const getAllAppointments = () =>
  axiosInstance.get("/api/admin/appointments");

export const getPatientAppointments = (patientId) =>
  axiosInstance.get(`/api/appointments/patient/${patientId}`);

export const getDoctorAppointments = (doctorId) =>
  axiosInstance.get(`/api/appointments/doctor/${doctorId}`);

export const updateAppointmentStatus = (id, status) =>
  axiosInstance.put(`/api/appointments/${id}/status`, null, {
    params: { status },
  });