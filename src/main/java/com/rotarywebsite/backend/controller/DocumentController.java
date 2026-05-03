package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.dto.DocumentResponseDto;
import com.rotarywebsite.backend.model.Document;
import com.rotarywebsite.backend.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documentos")
public class DocumentController {

    private final DocumentService documentoService;

    public DocumentController(DocumentService documentoService) {
        this.documentoService = documentoService;
    }

    @PostMapping("/proyecto/{proyectoId}")
    public ResponseEntity<?> subirDocumentoProyecto(
            @PathVariable Long proyectoId,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam(value = "descripcion", required = false) String descripcion) {
        try {
            Document documento = documentoService.saveProjectDocument(archivo, proyectoId, descripcion);
            DocumentResponseDto response = toDto(documento);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno al subir documento"));
        }
    }

    @PostMapping("/noticia/{noticiaId}")
    public ResponseEntity<?> subirDocumentoNoticia(
            @PathVariable Long noticiaId,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam(value = "descripcion", required = false) String descripcion) {
        try {
            Document documento = documentoService.saveNewsDocument(archivo, noticiaId, descripcion);
            DocumentResponseDto response = toDto(documento);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno al subir documento"));
        }
    }

    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<?> obtenerPorProyecto(@PathVariable Long proyectoId) {
        try {
            List<DocumentResponseDto> documentos = documentoService.getByProject(proyectoId)
                    .stream()
                    .map(this::toDto)
                    .toList();

            return ResponseEntity.ok(documentos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/noticia/{noticiaId}")
    public ResponseEntity<?> obtenerPorNoticia(@PathVariable Long noticiaId) {
        try {
            List<DocumentResponseDto> documentos = documentoService.getByNews(noticiaId)
                    .stream()
                    .map(this::toDto)
                    .toList();

            return ResponseEntity.ok(documentos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Document documento = documentoService.getById(id);
            return ResponseEntity.ok(toDto(documento));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}/download-url")
    public ResponseEntity<?> obtenerUrlDescarga(@PathVariable Long id) {
        try {
            String downloadUrl = documentoService.getDownloadUrl(id);
            return ResponseEntity.ok(Map.of("downloadUrl", downloadUrl));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarDocumento(@PathVariable Long id) {
        try {
            documentoService.deleteDocument(id);
            return ResponseEntity.ok(Map.of("mensaje", "Documento eliminado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    private DocumentResponseDto toDto(Document documento) {
        String downloadUrl;
        try {
            downloadUrl = documentoService.getDownloadUrl(documento.getId());
        } catch (Exception e) {
            downloadUrl = null;
        }

        return new DocumentResponseDto(
                documento.getId(),
                documento.getNombreArchivo(),
                documento.getContentType(),
                documento.getSizeBytes(),
                documento.getFechaSubida(),
                downloadUrl
        );
    }
}