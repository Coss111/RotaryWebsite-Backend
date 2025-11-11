package com.rotarywebsite.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    
    @Enumerated(EnumType.STRING)
    private ReportType tipo;
    
    @Column(length = 2000)
    private String contenido; // JSON o datos del reporte
    
    private String urlArchivo; // Si se genera un archivo
    
    private LocalDateTime fechaGeneracion;
    
    // Relación con quien generó el reporte
    @ManyToOne
    @JoinColumn(name = "generado_por_id")
    private Member generadoPor;
    
    // CONSTRUCTORES
    public Report() {}
    
    public Report(String nombre, ReportType tipo, Member generadoPor) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.generadoPor = generadoPor;
        this.fechaGeneracion = LocalDateTime.now();
    }
    
    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public ReportType getTipo() { return tipo; }
    public void setTipo(ReportType tipo) { this.tipo = tipo; }
    
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    
    public String getUrlArchivo() { return urlArchivo; }
    public void setUrlArchivo(String urlArchivo) { this.urlArchivo = urlArchivo; }
    
    public LocalDateTime getFechaGeneracion() { return fechaGeneracion; }
    public void setFechaGeneracion(LocalDateTime fechaGeneracion) { this.fechaGeneracion = fechaGeneracion; }
    
    public Member getGeneradoPor() { return generadoPor; }
    public void setGeneradoPor(Member generadoPor) { this.generadoPor = generadoPor; }
}