import axiosInstance from "../api/axios";

export const register = (data) =>
  axiosInstance.post("/auth/register", data);

export const login = (data) =>
  axiosInstance.post("/auth/login", data);

export const getProfile = () =>
  axiosInstance.get("/auth/profile");

export const getUserById = (id) =>
  axiosInstance.get(`/auth/users/${id}`);