package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.model.Document;
import com.rotarywebsite.backend.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documentos")
@CrossOrigin(origins = "*")
public class DocumentController {

    @Autowired
    private DocumentService documentoService;

    // Subir documento para proyecto
    @PostMapping("/proyecto/{proyectoId}")
    public ResponseEntity<?> subirDocumentoProyecto(
            @PathVariable Long proyectoId,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam(value = "descripcion", required = false) String descripcion) {
        try {
            Document documento = documentoService.saveProjectDocument(archivo, proyectoId, descripcion);
            return ResponseEntity.ok(documento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Subir documento para noticia
    @PostMapping("/noticia/{noticiaId}")
    public ResponseEntity<?> subirDocumentoNoticia(
            @PathVariable Long noticiaId,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam(value = "descripcion", required = false) String descripcion) {
        try {
            Document documento = documentoService.saveNewsDocument(archivo, noticiaId, descripcion);
            return ResponseEntity.ok(documento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener documentos por proyecto
    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<List<Document>> obtenerPorProyecto(@PathVariable Long proyectoId) {
        try {
            List<Document> documentos = documentoService.getByProject(proyectoId);
            return ResponseEntity.ok(documentos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Obtener documentos por noticia
    @GetMapping("/noticia/{noticiaId}")
    public ResponseEntity<List<Document>> obtenerPorNoticia(@PathVariable Long noticiaId) {
        try {
            List<Document> documentos = documentoService.getByNews(noticiaId);
            return ResponseEntity.ok(documentos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Eliminar documento
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarDocumento(@PathVariable Long id) {
        try {
            documentoService.deleteDocument(id);
            return ResponseEntity.ok(Map.of("mensaje", "Documento eliminado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener documento por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Document documento = documentoService.getById(id);
            return ResponseEntity.ok(documento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}