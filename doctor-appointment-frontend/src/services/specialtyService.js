import axiosInstance from "../api/axios";

export const getAllSpecialties = () =>
  axiosInstance.get("/api/specialties");

export const getSpecialtyById = (id) =>
  axiosInstance.get(`/api/specialties/${id}`);

export const createSpecialty = (data) =>
  axiosInstance.post("/api/admin/specialties", data);