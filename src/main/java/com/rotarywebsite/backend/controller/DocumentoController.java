package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.model.Documento;
import com.rotarywebsite.backend.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documentos")
@CrossOrigin(origins = "*")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    // Subir documento para proyecto
    @PostMapping("/proyecto/{proyectoId}")
    public ResponseEntity<?> subirDocumentoProyecto(
            @PathVariable Long proyectoId,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam(value = "descripcion", required = false) String descripcion) {
        try {
            Documento documento = documentoService.guardarDocumentoProyecto(archivo, proyectoId, descripcion);
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
            Documento documento = documentoService.guardarDocumentoNoticia(archivo, noticiaId, descripcion);
            return ResponseEntity.ok(documento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener documentos por proyecto
    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<List<Documento>> obtenerPorProyecto(@PathVariable Long proyectoId) {
        try {
            List<Documento> documentos = documentoService.obtenerPorProyecto(proyectoId);
            return ResponseEntity.ok(documentos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Obtener documentos por noticia
    @GetMapping("/noticia/{noticiaId}")
    public ResponseEntity<List<Documento>> obtenerPorNoticia(@PathVariable Long noticiaId) {
        try {
            List<Documento> documentos = documentoService.obtenerPorNoticia(noticiaId);
            return ResponseEntity.ok(documentos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Eliminar documento
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarDocumento(@PathVariable Long id) {
        try {
            documentoService.eliminarDocumento(id);
            return ResponseEntity.ok(Map.of("mensaje", "Documento eliminado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener documento por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Documento documento = documentoService.obtenerPorId(id);
            return ResponseEntity.ok(documento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}