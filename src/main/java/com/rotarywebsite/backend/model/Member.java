package com.rotarywebsite.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "miembros")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String telefono;
    private String ocupacion;
    private String direccion;
    
    @Enumerated(EnumType.STRING)
    private MembershipStatus estadoMembresia = MembershipStatus.ACTIVE;
    
    private LocalDate fechaIngreso;
    private LocalDate fechaRenovacion;
    
    // Relación con Usuario
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;
    
    // CONSTRUCTORES
    public Member() {}
    
    public Member(String nombre, String telefono, String ocupacion, User usuario) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.ocupacion = ocupacion;
        this.usuario = usuario;
        this.fechaIngreso = LocalDate.now();
        this.fechaRenovacion = LocalDate.now().plusYears(1);
    }
    
    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getOcupacion() { return ocupacion; }
    public void setOcupacion(String ocupacion) { this.ocupacion = ocupacion; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public MembershipStatus getEstadoMembresia() { return estadoMembresia; }
    public void setEstadoMembresia(MembershipStatus estadoMembresia) { this.estadoMembresia = estadoMembresia; }
    
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    
    public LocalDate getFechaRenovacion() { return fechaRenovacion; }
    public void setFechaRenovacion(LocalDate fechaRenovacion) { this.fechaRenovacion = fechaRenovacion; }
    
    public User getUsuario() { return usuario; }
    public void setUsuario(User usuario) { this.usuario = usuario; }
}