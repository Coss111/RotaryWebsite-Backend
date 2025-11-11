package com.rotarywebsite.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String password;
    
    @Enumerated(EnumType.STRING)
    private RolUsuario rol;
    
    private Boolean activo = true;
    
    private LocalDateTime fechaRegistro;
    
    private LocalDateTime ultimoLogin;
    
    // CONSTRUCTORES
    public Usuario() {}
    
    public Usuario(String email, String password, RolUsuario rol) {
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.fechaRegistro = LocalDateTime.now();
    }
    
    // GETTERS Y SETTERS (generar con Lombok o manualmente)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public RolUsuario getRol() { return rol; }
    public void setRol(RolUsuario rol) { this.rol = rol; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public LocalDateTime getUltimoLogin() { return ultimoLogin; }
    public void setUltimoLogin(LocalDateTime ultimoLogin) { this.ultimoLogin = ultimoLogin; }
}