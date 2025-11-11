package com.rotarywebsite.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "noticias")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titulo;
    
    @Column(length = 500)
    private String lead; // Primer párrafo
    
    @Column(length = 2000)
    private String contenido;
    
    private LocalDate fechaPublicacion;
    
    // Estructura LEAD
    private String que;        // ¿Qué sucedió?
    private String cuando;     // ¿Cuándo sucedió?
    private String donde;      // ¿Dónde sucedió?
    private String porQue;     // ¿Por qué sucedió?
    private String como;       // ¿Cómo sucedió?
    
    // Relación con Miembro (autor)
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Member autor;
    
    // Documentos/fotos de la noticia
    @OneToMany(mappedBy = "noticia", cascade = CascadeType.ALL)
    private List<Document> documentos = new ArrayList<>();
    
    // CONSTRUCTORES
    public News() {}
    
    public News(String titulo, String lead, Member autor) {
        this.titulo = titulo;
        this.lead = lead;
        this.autor = autor;
        this.fechaPublicacion = LocalDate.now();
    }
    
    // GETTERS Y SETTERS (generar todos)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getLead() { return lead; }
    public void setLead(String lead) { this.lead = lead; }
    
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    
    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
    
    public String getQue() { return que; }
    public void setQue(String que) { this.que = que; }
    
    public String getCuando() { return cuando; }
    public void setCuando(String cuando) { this.cuando = cuando; }
    
    public String getDonde() { return donde; }
    public void setDonde(String donde) { this.donde = donde; }
    
    public String getPorQue() { return porQue; }
    public void setPorQue(String porQue) { this.porQue = porQue; }
    
    public String getComo() { return como; }
    public void setComo(String como) { this.como = como; }
    
    public Member getAutor() { return autor; }
    public void setAutor(Member autor) { this.autor = autor; }
    
    public List<Document> getDocumentos() { return documentos; }
    public void setDocumentos(List<Document> documentos) { this.documentos = documentos; }
}