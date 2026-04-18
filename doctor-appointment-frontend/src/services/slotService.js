import axiosInstance from "../api/axios";

export const createSlot = (data) =>
  axiosInstance.post("/slots", data);

export const getSlotsByDoctorAndDate = (doctorId, date) =>
  axiosInstance.get("/slots", { params: { doctorId, date } });

export const getAvailableSlots = (doctorId, date) =>
  axiosInstance.get("/slots/available", { params: { doctorId, date } });

export const getAllSlotsByDoctor = (doctorId) =>
  axiosInstance.get(`/slots/doctor/${doctorId}`);

export const deleteSlot = (id) =>
  axiosInstance.delete(`/slots/${id}`);