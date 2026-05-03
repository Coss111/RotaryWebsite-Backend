package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByProyectoId(Long proyectoId);

    List<Document> findByNoticiaId(Long noticiaId);

    List<Document> findByContentTypeContainingIgnoreCase(String contentType);

    List<Document> findByNombreArchivoContainingIgnoreCase(String nombreArchivo);

    Optional<Document> findByObjectName(String objectName);

    long countByProyectoId(Long proyectoId);

    long countByNoticiaId(Long noticiaId);
}