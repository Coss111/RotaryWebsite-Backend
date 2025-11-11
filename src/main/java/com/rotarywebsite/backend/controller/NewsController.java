package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.model.News;
import com.rotarywebsite.backend.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/noticias")
@CrossOrigin(origins = "*")
public class NewsController {

    @Autowired
    private NewsService noticiaService;

    // Obtener todas las noticias
    @GetMapping
    public ResponseEntity<List<News>> obtenerTodas() {
        List<News> noticias = noticiaService.getAll();
        return ResponseEntity.ok(noticias);
    }

    // Obtener últimas noticias
    @GetMapping("/ultimas")
    public ResponseEntity<List<News>> obtenerUltimas() {
        List<News> noticias = noticiaService.getLatestNews();
        return ResponseEntity.ok(noticias);
    }

    // Obtener noticia por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            News noticia = noticiaService.getById(id);
            return ResponseEntity.ok(noticia);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Crear nueva noticia
    @PostMapping
    public ResponseEntity<?> crearNoticia(@RequestBody Map<String, Object> request) {
        try {
            String titulo = (String) request.get("titulo");
            String lead = (String) request.get("lead");
            String contenido = (String) request.get("contenido");
            Long autorId = Long.valueOf(request.get("autorId").toString());
            String que = (String) request.get("que");
            String cuando = (String) request.get("cuando");
            String donde = (String) request.get("donde");
            String porQue = (String) request.get("porQue");
            String como = (String) request.get("como");

            News noticia = noticiaService.createNews(
                titulo, lead, contenido, autorId, que, cuando, donde, porQue, como
            );
            return ResponseEntity.ok(noticia);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Actualizar noticia
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarNoticia(@PathVariable Long id, @RequestBody News noticiaActualizada) {
        try {
            News noticia = noticiaService.updateNews(id, noticiaActualizada);
            return ResponseEntity.ok(noticia);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Eliminar noticia
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarNoticia(@PathVariable Long id) {
        try {
            noticiaService.deleteNews(id);
            return ResponseEntity.ok(Map.of("mensaje", "Noticia eliminada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Buscar noticias por título
    @GetMapping("/buscar")
    public ResponseEntity<List<News>> buscarPorTitulo(@RequestParam String titulo) {
        List<News> noticias = noticiaService.searchByTitle(titulo);
        return ResponseEntity.ok(noticias);
    }
}