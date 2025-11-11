package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.Document;
import com.rotarywebsite.backend.model.Project;
import com.rotarywebsite.backend.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    
    // Buscar documentos por proyecto
    List<Document> findByProyecto(Project proyecto);
    
    // Buscar documentos por noticia
    List<Document> findByNoticia(News noticia);
    
    // Buscar documentos por tipo de contenido
    List<Document> findByTipoContenidoContaining(String tipoContenido);
    
    // Buscar documentos por nombre de archivo
    List<Document> findByNombreArchivoContainingIgnoreCase(String nombre);
    
    // Contar documentos por proyecto
    long countByProyecto(Project proyecto);
}