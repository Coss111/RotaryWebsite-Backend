package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.model.Documento;
import com.rotarywebsite.backend.model.Proyecto;
import com.rotarywebsite.backend.model.Noticia;
import com.rotarywebsite.backend.repository.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private NoticiaService noticiaService;

    // Guardar documento para proyecto
    public Documento guardarDocumentoProyecto(MultipartFile archivo, Long proyectoId, String descripcion) {
        try {
            Proyecto proyecto = proyectoService.obtenerPorId(proyectoId);
            
            // Subir archivo a MinIO
            String fileName = fileStorageService.uploadFile(archivo);
            String fileUrl = fileStorageService.getFileUrl(fileName);

            // Crear documento en BD
            Documento documento = new Documento(
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
    public Documento guardarDocumentoNoticia(MultipartFile archivo, Long noticiaId, String descripcion) {
        try {
            Noticia noticia = noticiaService.obtenerPorId(noticiaId);
            
            String fileName = fileStorageService.uploadFile(archivo);
            String fileUrl = fileStorageService.getFileUrl(fileName);

            Documento documento = new Documento(
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
    public List<Documento> obtenerPorProyecto(Long proyectoId) {
        Proyecto proyecto = proyectoService.obtenerPorId(proyectoId);
        return documentoRepository.findByProyecto(proyecto);
    }

    // Obtener documentos por noticia
    public List<Documento> obtenerPorNoticia(Long noticiaId) {
        Noticia noticia = noticiaService.obtenerPorId(noticiaId);
        return documentoRepository.findByNoticia(noticia);
    }

    // Eliminar documento
    public void eliminarDocumento(Long documentoId) {
        Documento documento = documentoRepository.findById(documentoId)
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
    public Documento obtenerPorId(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
    }
}