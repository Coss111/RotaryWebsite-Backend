package com.rotarywebsite.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    
    @Enumerated(EnumType.STRING)
    private TipoReporte tipo;
    
    @Column(length = 2000)
    private String contenido; // JSON o datos del reporte
    
    private String urlArchivo; // Si se genera un archivo
    
    private LocalDateTime fechaGeneracion;
    
    // Relación con quien generó el reporte
    @ManyToOne
    @JoinColumn(name = "generado_por_id")
    private Miembro generadoPor;
    
    // CONSTRUCTORES
    public Reporte() {}
    
    public Reporte(String nombre, TipoReporte tipo, Miembro generadoPor) {
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
    
    public TipoReporte getTipo() { return tipo; }
    public void setTipo(TipoReporte tipo) { this.tipo = tipo; }
    
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    
    public String getUrlArchivo() { return urlArchivo; }
    public void setUrlArchivo(String urlArchivo) { this.urlArchivo = urlArchivo; }
    
    public LocalDateTime getFechaGeneracion() { return fechaGeneracion; }
    public void setFechaGeneracion(LocalDateTime fechaGeneracion) { this.fechaGeneracion = fechaGeneracion; }
    
    public Miembro getGeneradoPor() { return generadoPor; }
    public void setGeneradoPor(Miembro generadoPor) { this.generadoPor = generadoPor; }
}