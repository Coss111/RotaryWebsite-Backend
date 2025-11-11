package com.rotarywebsite.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "documentos")
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombreArchivo;
    private String url; // URL en MinIO
    private Long tamaño;
    private String tipoContenido;
    
    private LocalDateTime fechaSubida;
    
    // Relaciones (documento puede pertenecer a Proyecto o Noticia)
    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;
    
    @ManyToOne
    @JoinColumn(name = "noticia_id")
    private Noticia noticia;
    
    // CONSTRUCTORES
    public Documento() {}
    
    public Documento(String nombreArchivo, String url, String tipoContenido) {
        this.nombreArchivo = nombreArchivo;
        this.url = url;
        this.tipoContenido = tipoContenido;
        this.fechaSubida = LocalDateTime.now();
    }
    
    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombreArchivo() { return nombreArchivo; }
    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public Long getTamaño() { return tamaño; }
    public void setTamaño(Long tamaño) { this.tamaño = tamaño; }
    
    public String getTipoContenido() { return tipoContenido; }
    public void setTipoContenido(String tipoContenido) { this.tipoContenido = tipoContenido; }
    
    public LocalDateTime getFechaSubida() { return fechaSubida; }
    public void setFechaSubida(LocalDateTime fechaSubida) { this.fechaSubida = fechaSubida; }
    
    public Proyecto getProyecto() { return proyecto; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }
    
    public Noticia getNoticia() { return noticia; }
    public void setNoticia(Noticia noticia) { this.noticia = noticia; }
}