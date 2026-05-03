package com.rotarywebsite.backend.dto;

import com.rotarywebsite.backend.model.UserRole;

import java.time.LocalDateTime;

public class UserDTO {

    private Long id;
    private String email;
    private UserRole role;
    private Boolean active;
    private LocalDateTime registrationDate;
    private LocalDateTime lastLogin;

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }

    public Boolean getActive() {
        return active;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}