import axiosInstance from "../api/axios";

export const getAllDoctors = () =>
  axiosInstance.get("/api/doctors");

export const getDoctorById = (id) =>
  axiosInstance.get(`/api/doctors/${id}`);

export const filterDoctors = (specialtyId, mode) => {
  const params = {};
  if (specialtyId) params.specialtyId = specialtyId;
  if (mode) params.mode = mode;
  return axiosInstance.get("/api/doctors/filter", { params });
};

export const createDoctor = (data) =>
  axiosInstance.post("/api/admin/doctors", data);