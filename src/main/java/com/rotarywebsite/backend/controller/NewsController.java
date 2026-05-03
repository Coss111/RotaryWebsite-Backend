package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.dto.NewsDTO;
import com.rotarywebsite.backend.model.News;
import com.rotarywebsite.backend.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/noticias")
public class NewsController {

    private final NewsService noticiaService;

    public NewsController(NewsService noticiaService) {
        this.noticiaService = noticiaService;
    }

    @GetMapping
    public ResponseEntity<List<NewsDTO>> obtenerTodas() {
        List<NewsDTO> noticias = noticiaService.getAll()
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(noticias);
    }

    @GetMapping("/ultimas")
    public ResponseEntity<List<NewsDTO>> obtenerUltimas() {
        List<NewsDTO> noticias = noticiaService.getLatestNews()
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(noticias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            News noticia = noticiaService.getById(id);
            return ResponseEntity.ok(toDto(noticia));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> crearNoticia(@RequestBody NewsDTO dto) {
        try {
            News noticia = noticiaService.createNews(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDto(noticia));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarNoticia(@PathVariable Long id, @RequestBody NewsDTO dto) {
        try {
            News noticia = noticiaService.updateNews(id, dto);
            return ResponseEntity.ok(toDto(noticia));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarNoticia(@PathVariable Long id) {
        try {
            noticiaService.deleteNews(id);
            return ResponseEntity.ok(Map.of("mensaje", "Noticia eliminada exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<NewsDTO>> buscarPorTitulo(@RequestParam String titulo) {
        List<NewsDTO> noticias = noticiaService.searchByTitle(titulo)
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(noticias);
    }

    @GetMapping("/buscar-contenido")
    public ResponseEntity<List<NewsDTO>> buscarPorContenido(@RequestParam String texto) {
        List<NewsDTO> noticias = noticiaService.searchByContent(texto)
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(noticias);
    }

    private NewsDTO toDto(News noticia) {
        NewsDTO dto = new NewsDTO();
        dto.setId(noticia.getId());
        dto.setTitle(noticia.getTitulo());
        dto.setLead(noticia.getLead());
        dto.setContent(noticia.getContenido());
        dto.setPublicationDate(noticia.getFechaPublicacion());
        dto.setWhat(noticia.getQue());
        dto.setWhen(noticia.getCuando());
        dto.setWhere(noticia.getDonde());
        dto.setWhy(noticia.getPorQue());
        dto.setHow(noticia.getComo());

        if (noticia.getAutor() != null) {
            dto.setAuthorId(noticia.getAutor().getId());
            dto.setAuthorName(noticia.getAutor().getNombre());
        }

        return dto;
    }
}