package com.doctor.appointment.module.AuthModule.dto;

import com.doctor.appointment.module.AuthModule.entity.RoleName;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private RoleName role;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public RoleName getRole() { return role; }
    public void setRole(RoleName role) { this.role = role; }
}