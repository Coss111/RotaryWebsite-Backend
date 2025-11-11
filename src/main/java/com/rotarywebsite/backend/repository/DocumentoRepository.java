package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.Documento;
import com.rotarywebsite.backend.model.Proyecto;
import com.rotarywebsite.backend.model.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    
    // Buscar documentos por proyecto
    List<Documento> findByProyecto(Proyecto proyecto);
    
    // Buscar documentos por noticia
    List<Documento> findByNoticia(Noticia noticia);
    
    // Buscar documentos por tipo de contenido
    List<Documento> findByTipoContenidoContaining(String tipoContenido);
    
    // Buscar documentos por nombre de archivo
    List<Documento> findByNombreArchivoContainingIgnoreCase(String nombre);
    
    // Contar documentos por proyecto
    long countByProyecto(Proyecto proyecto);
}