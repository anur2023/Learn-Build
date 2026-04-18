package com.doctor.appointment.module.AuthModule.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName roleName;

    public enum RoleName {
        PATIENT, DOCTOR, ADMIN
    }

    // ─── No-Args Constructor (required by JPA) ───
    public Role() {}

    // ─── All-Args Constructor ───
    public Role(Long id, RoleName roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    // ─── Getters & Setters ───
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public RoleName getRoleName() { return roleName; }
    public void setRoleName(RoleName roleName) { this.roleName = roleName; }
}