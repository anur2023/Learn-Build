package com.doctor.appointment.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // ─── Public Routes ───
                        .requestMatchers("/auth/register", "/auth/login").permitAll()

                        // ─── Admin Only ───
                        // BUG FIX #4: Controllers use /api prefix, so security rules
                        // must match /api/admin/**, not just /admin/**. Same applies
                        // to all routes below.
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // ─── Doctor & Admin ───
                        // BUG FIX #4: /slots/** is served by SlotController at /slots
                        // (no /api prefix) — keep as-is. /api/doctors/** updated.
                        .requestMatchers("/slots").hasAnyRole("DOCTOR", "ADMIN")
                        .requestMatchers("/slots/doctor/**").hasAnyRole("DOCTOR", "ADMIN")
                        .requestMatchers("/api/doctors/**").hasAnyRole("DOCTOR", "ADMIN", "PATIENT")

                        // ─── Patients can view available slots for booking ───
                        // BUG FIX #5: Patients must be able to GET /slots/available and
                        // GET /slots (by date) to browse slots before booking.
                        // These are read-only and should be accessible to PATIENT too.
                        .requestMatchers("/slots/available").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")
                        .requestMatchers("/slots").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")

                        // ─── Patient, Doctor & Admin ───
                        .requestMatchers("/api/appointments/**").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")
                        .requestMatchers("/api/specialties/**").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")

                        // ─── All Authenticated Users ───
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}