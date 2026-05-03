package com.rotarywebsite.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String lead;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "fecha_publicacion", nullable = false)
    private LocalDate fechaPublicacion;

    @Column(length = 255)
    private String que;

    @Column(length = 255)
    private String cuando;

    @Column(length = 255)
    private String donde;

    @Column(length = 255)
    private String porQue;

    @Column(length = 255)
    private String como;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    private Member autor;

    @OneToMany(mappedBy = "noticia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Document> documentos = new ArrayList<>();

    public News() {
    }

    public News(String titulo, String lead, Member autor) {
        this.titulo = titulo;
        this.lead = lead;
        this.autor = autor;
        this.fechaPublicacion = LocalDate.now();
    }

    @PrePersist
    protected void onCreate() {
        if (this.fechaPublicacion == null) {
            this.fechaPublicacion = LocalDate.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLead() {
        return lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getQue() {
        return que;
    }

    public void setQue(String que) {
        this.que = que;
    }

    public String getCuando() {
        return cuando;
    }

    public void setCuando(String cuando) {
        this.cuando = cuando;
    }

    public String getDonde() {
        return donde;
    }

    public void setDonde(String donde) {
        this.donde = donde;
    }

    public String getPorQue() {
        return porQue;
    }

    public void setPorQue(String porQue) {
        this.porQue = porQue;
    }

    public String getComo() {
        return como;
    }

    public void setComo(String como) {
        this.como = como;
    }

    public Member getAutor() {
        return autor;
    }

    public void setAutor(Member autor) {
        this.autor = autor;
    }

    public List<Document> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Document> documentos) {
        this.documentos = documentos;
    }
}