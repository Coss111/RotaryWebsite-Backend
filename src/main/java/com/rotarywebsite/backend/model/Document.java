package com.rotarywebsite.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "documentos")
public class Document {
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
    private Project proyecto;
    
    @ManyToOne
    @JoinColumn(name = "noticia_id")
    private News noticia;
    
    // CONSTRUCTORES
    public Document() {}
    
    public Document(String nombreArchivo, String url, String tipoContenido) {
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
    
    public Project getProyecto() { return proyecto; }
    public void setProyecto(Project proyecto) { this.proyecto = proyecto; }
    
    public News getNoticia() { return noticia; }
    public void setNoticia(News noticia) { this.noticia = noticia; }
}