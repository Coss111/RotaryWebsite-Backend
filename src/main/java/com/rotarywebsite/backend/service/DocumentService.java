package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.model.Document;
import com.rotarywebsite.backend.model.Project;
import com.rotarywebsite.backend.model.News;
import com.rotarywebsite.backend.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentoRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ProjectService proyectoService;

    @Autowired
    private NewsService noticiaService;

    // Guardar documento para proyecto
    public Document guardarDocumentoProyecto(MultipartFile archivo, Long proyectoId, String descripcion) {
        try {
            Project proyecto = proyectoService.obtenerPorId(proyectoId);
            
            // Subir archivo a MinIO
            String fileName = fileStorageService.uploadFile(archivo);
            String fileUrl = fileStorageService.getFileUrl(fileName);

            // Crear documento en BD
            Document documento = new Document(
                archivo.getOriginalFilename(), 
                fileUrl, 
                archivo.getContentType()
            );
            documento.setProyecto(proyecto);
            documento.setTamaño(archivo.getSize());

            return documentoRepository.save(documento);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar documento: " + e.getMessage());
        }
    }

    // Guardar documento para noticia
    public Document guardarDocumentoNoticia(MultipartFile archivo, Long noticiaId, String descripcion) {
        try {
            News noticia = noticiaService.obtenerPorId(noticiaId);
            
            String fileName = fileStorageService.uploadFile(archivo);
            String fileUrl = fileStorageService.getFileUrl(fileName);

            Document documento = new Document(
                archivo.getOriginalFilename(), 
                fileUrl, 
                archivo.getContentType()
            );
            documento.setNoticia(noticia);
            documento.setTamaño(archivo.getSize());

            return documentoRepository.save(documento);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar documento: " + e.getMessage());
        }
    }

    // Obtener documentos por proyecto
    public List<Document> obtenerPorProyecto(Long proyectoId) {
        Project proyecto = proyectoService.obtenerPorId(proyectoId);
        return documentoRepository.findByProyecto(proyecto);
    }

    // Obtener documentos por noticia
    public List<Document> obtenerPorNoticia(Long noticiaId) {
        News noticia = noticiaService.obtenerPorId(noticiaId);
        return documentoRepository.findByNoticia(noticia);
    }

    // Eliminar documento
    public void eliminarDocumento(Long documentoId) {
        Document documento = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
        
        // Eliminar de MinIO
        try {
            fileStorageService.deleteFile(documento.getUrl());
        } catch (Exception e) {
            System.err.println("Error al eliminar archivo de MinIO: " + e.getMessage());
        }
        
        // Eliminar de BD
        documentoRepository.delete(documento);
    }

    // Obtener documento por ID
    public Document obtenerPorId(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
    }
}