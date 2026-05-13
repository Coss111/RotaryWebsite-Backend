package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.model.Document;
import com.rotarywebsite.backend.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DocumentService {

    // Definimos las extensiones permitidas
    private final List<String> ALLOWED_EXTENSIONS = List.of("pdf", "jpg", "jpeg", "png", "docx");
    private final DocumentRepository documentoRepository;
    private final FileStorageService fileStorageService;
    private final ProjectService proyectoService;
    private final NewsService noticiaService;

    public DocumentService(
            DocumentRepository documentoRepository,
            FileStorageService fileStorageService,
            ProjectService proyectoService,
            NewsService noticiaService
    ) {
        this.documentoRepository = documentoRepository;
        this.fileStorageService = fileStorageService;
        this.proyectoService = proyectoService;
        this.noticiaService = noticiaService;
    }

    @Transactional
    public Document saveProjectDocument(MultipartFile archivo, Long proyectoId, String descripcion) {
        validarArchivo(archivo);
        try {
            // Validar que el proyecto exista
            var proyecto = proyectoService.getById(proyectoId);

            // Subir archivo a MinIO y obtener la clave real del objeto
            String objectName = fileStorageService.uploadFile(archivo);

            // Crear documento en BD
            Document documento = new Document(
                    archivo.getOriginalFilename(),
                    objectName,
                    archivo.getContentType(),
                    archivo.getSize()
            );

            documento.setProyecto(proyecto);

            // Por ahora "descripcion" no se guarda porque tu entity Document no tiene ese campo
            return documentoRepository.save(documento);

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar documento del proyecto: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Document saveNewsDocument(MultipartFile archivo, Long noticiaId, String descripcion) {
        try {
            // Validar que la noticia exista
            var noticia = noticiaService.getById(noticiaId);

            // Subir archivo a MinIO y obtener la clave real del objeto
            String objectName = fileStorageService.uploadFile(archivo);

            // Crear documento en BD
            Document documento = new Document(
                    archivo.getOriginalFilename(),
                    objectName,
                    archivo.getContentType(),
                    archivo.getSize()
            );

            documento.setNoticia(noticia);

            // Por ahora "descripcion" no se guarda porque tu entity Document no tiene ese campo
            return documentoRepository.save(documento);

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar documento de la noticia: " + e.getMessage(), e);
        }
    }

    @Transactional(readOnly = true)
    public List<Document> getByProject(Long proyectoId) {
        // Validar existencia
        proyectoService.getById(proyectoId);
        return documentoRepository.findByProyectoId(proyectoId);
    }

    @Transactional(readOnly = true)
    public List<Document> getByNews(Long noticiaId) {
        // Validar existencia
        noticiaService.getById(noticiaId);
        return documentoRepository.findByNoticiaId(noticiaId);
    }

    @Transactional(readOnly = true)
    public Document getById(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado con id: " + id));
    }

    @Transactional
    public void deleteDocument(Long documentoId) {
        Document documento = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado con id: " + documentoId));

        // Eliminar archivo en MinIO usando objectName
        try {
            fileStorageService.deleteFile(documento.getObjectName());
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar archivo de MinIO: " + e.getMessage(), e);
        }

        // Eliminar registro en BD
        documentoRepository.delete(documento);
    }

    @Transactional(readOnly = true)
    public String getDownloadUrl(Long documentoId) {
        Document documento = getById(documentoId);
        return fileStorageService.getFileUrl(documento.getObjectName());
    }

    private void validarArchivo(MultipartFile archivo) {
    if (archivo.isEmpty()) {
        throw new RuntimeException("No se puede subir un archivo vacío.");
    }

    String nombreArchivo = archivo.getOriginalFilename();
    if (nombreArchivo == null || !nombreArchivo.contains(".")) {
        throw new RuntimeException("Nombre de archivo inválido.");
    }

    // Extraer extensión
    String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1).toLowerCase();

    if (!ALLOWED_EXTENSIONS.contains(extension)) {
        throw new RuntimeException("Extensión de archivo no permitida: ." + extension + 
                                   ". Solo se admiten: " + ALLOWED_EXTENSIONS);
    }
    
    // Validación extra opcional: Tamaño (ej. 5MB)
    if (archivo.getSize() > 5 * 1024 * 1024) {
        throw new RuntimeException("El archivo excede el límite de 5MB.");
    }
}
}
