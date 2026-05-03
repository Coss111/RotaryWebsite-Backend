package com.rotarywebsite.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "miembros")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 30)
    private String telefono;

    @Column(length = 150)
    private String ocupacion;

    @Column(length = 255)
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_membresia", nullable = false, length = 30)
    private MembershipStatus estadoMembresia = MembershipStatus.ACTIVE;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "fecha_renovacion", nullable = false)
    private LocalDate fechaRenovacion;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    @JsonIgnore
    private User usuario;

    public Member() {
    }

    public Member(String nombre, String telefono, String ocupacion, User usuario) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.ocupacion = ocupacion;
        this.usuario = usuario;
        this.fechaIngreso = LocalDate.now();
        this.fechaRenovacion = LocalDate.now().plusYears(1);
        this.estadoMembresia = MembershipStatus.ACTIVE;
    }

    @PrePersist
    protected void onCreate() {
        if (this.fechaIngreso == null) {
            this.fechaIngreso = LocalDate.now();
        }
        if (this.fechaRenovacion == null) {
            this.fechaRenovacion = LocalDate.now().plusYears(1);
        }
        if (this.estadoMembresia == null) {
            this.estadoMembresia = MembershipStatus.ACTIVE;
        }
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public MembershipStatus getEstadoMembresia() {
        return estadoMembresia;
    }

    public void setEstadoMembresia(MembershipStatus estadoMembresia) {
        this.estadoMembresia = estadoMembresia;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaRenovacion() {
        return fechaRenovacion;
    }

    public void setFechaRenovacion(LocalDate fechaRenovacion) {
        this.fechaRenovacion = fechaRenovacion;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
}