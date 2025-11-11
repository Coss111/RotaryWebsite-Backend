package com.rotarywebsite.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proyectos")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    
    @Column(length = 1000)
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    private EstadoProyecto estado = EstadoProyecto.PLANIFICACION;
    
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    
    private String impactoSocial;
    private Double presupuesto;
    
    // Campos del formulario
    private String responsable;
    private String lugar;
    private Integer numBeneficiarios;
    private Boolean participanNuevasGeneraciones = false;
    private Boolean participanOtrosClubes = false;
    private String institucionParticipante;
    private String urlMyRotary;
    
    // Coordenadas para el mapa
    private Double latitud;
    private Double longitud;
    
    // Relación con Miembro (creador)
    @ManyToOne
    @JoinColumn(name = "creador_id")
    private Miembro creador;
    
    // Documentos del proyecto
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private List<Documento> documentos = new ArrayList<>();
    
    // CONSTRUCTORES
    public Proyecto() {}
    
    public Proyecto(String nombre, String descripcion, Miembro creador) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creador = creador;
        this.fechaInicio = LocalDate.now();
    }
    
    // GETTERS Y SETTERS (generar todos)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public EstadoProyecto getEstado() { return estado; }
    public void setEstado(EstadoProyecto estado) { this.estado = estado; }
    
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    
    public String getImpactoSocial() { return impactoSocial; }
    public void setImpactoSocial(String impactoSocial) { this.impactoSocial = impactoSocial; }
    
    public Double getPresupuesto() { return presupuesto; }
    public void setPresupuesto(Double presupuesto) { this.presupuesto = presupuesto; }
    
    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
    
    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    
    public Integer getNumBeneficiarios() { return numBeneficiarios; }
    public void setNumBeneficiarios(Integer numBeneficiarios) { this.numBeneficiarios = numBeneficiarios; }
    
    public Boolean getParticipanNuevasGeneraciones() { return participanNuevasGeneraciones; }
    public void setParticipanNuevasGeneraciones(Boolean participanNuevasGeneraciones) { this.participanNuevasGeneraciones = participanNuevasGeneraciones; }
    
    public Boolean getParticipanOtrosClubes() { return participanOtrosClubes; }
    public void setParticipanOtrosClubes(Boolean participanOtrosClubes) { this.participanOtrosClubes = participanOtrosClubes; }
    
    public String getInstitucionParticipante() { return institucionParticipante; }
    public void setInstitucionParticipante(String institucionParticipante) { this.institucionParticipante = institucionParticipante; }
    
    public String getUrlMyRotary() { return urlMyRotary; }
    public void setUrlMyRotary(String urlMyRotary) { this.urlMyRotary = urlMyRotary; }
    
    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }
    
    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }
    
    public Miembro getCreador() { return creador; }
    public void setCreador(Miembro creador) { this.creador = creador; }
    
    public List<Documento> getDocumentos() { return documentos; }
    public void setDocumentos(List<Documento> documentos) { this.documentos = documentos; }
}