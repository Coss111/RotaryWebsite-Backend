package com.rotarywebsite.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proyectos")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ProjectStatus estado = ProjectStatus.PLANNING;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Lob
    @Column(name = "impacto_social", columnDefinition = "TEXT")
    private String impactoSocial;

    @Column(precision = 14, scale = 2)
    private BigDecimal presupuesto;

    @Column(length = 150)
    private String responsable;

    @Column(length = 255)
    private String lugar;

    @Column(name = "num_beneficiarios")
    private Integer numBeneficiarios;

    @Column(name = "participan_nuevas_generaciones", nullable = false)
    private boolean participanNuevasGeneraciones = false;

    @Column(name = "participan_otros_clubes", nullable = false)
    private boolean participanOtrosClubes = false;

    @Column(name = "institucion_participante", length = 255)
    private String institucionParticipante;

    @Column(name = "url_my_rotary", length = 500)
    private String urlMyRotary;

    @Column
    private Double latitud;

    @Column
    private Double longitud;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "creador_id", nullable = false)
    private Member creador;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Document> documentos = new ArrayList<>();

    public Project() {
    }

    public Project(String nombre, String descripcion, Member creador) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creador = creador;
        this.fechaInicio = LocalDate.now();
        this.estado = ProjectStatus.PLANNING;
    }

    @PrePersist
    protected void onCreate() {
        if (this.fechaInicio == null) {
            this.fechaInicio = LocalDate.now();
        }
        if (this.estado == null) {
            this.estado = ProjectStatus.PLANNING;
        }
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ProjectStatus getEstado() {
        return estado;
    }

    public void setEstado(ProjectStatus estado) {
        this.estado = estado;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getImpactoSocial() {
        return impactoSocial;
    }

    public void setImpactoSocial(String impactoSocial) {
        this.impactoSocial = impactoSocial;
    }

    public BigDecimal getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(BigDecimal presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Integer getNumBeneficiarios() {
        return numBeneficiarios;
    }

    public void setNumBeneficiarios(Integer numBeneficiarios) {
        this.numBeneficiarios = numBeneficiarios;
    }

    public boolean isParticipanNuevasGeneraciones() {
        return participanNuevasGeneraciones;
    }

    public void setParticipanNuevasGeneraciones(boolean participanNuevasGeneraciones) {
        this.participanNuevasGeneraciones = participanNuevasGeneraciones;
    }

    public boolean isParticipanOtrosClubes() {
        return participanOtrosClubes;
    }

    public void setParticipanOtrosClubes(boolean participanOtrosClubes) {
        this.participanOtrosClubes = participanOtrosClubes;
    }

    public String getInstitucionParticipante() {
        return institucionParticipante;
    }

    public void setInstitucionParticipante(String institucionParticipante) {
        this.institucionParticipante = institucionParticipante;
    }

    public String getUrlMyRotary() {
        return urlMyRotary;
    }

    public void setUrlMyRotary(String urlMyRotary) {
        this.urlMyRotary = urlMyRotary;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Member getCreador() {
        return creador;
    }

    public void setCreador(Member creador) {
        this.creador = creador;
    }

    public List<Document> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Document> documentos) {
        this.documentos = documentos;
    }
}