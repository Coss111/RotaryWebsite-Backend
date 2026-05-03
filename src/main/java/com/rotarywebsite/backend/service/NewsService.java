package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.dto.NewsDTO;
import com.rotarywebsite.backend.model.Member;
import com.rotarywebsite.backend.model.News;
import com.rotarywebsite.backend.repository.NewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class NewsService {

    private final NewsRepository noticiaRepository;
    private final MemberService miembroService;

    public NewsService(NewsRepository noticiaRepository, MemberService miembroService) {
        this.noticiaRepository = noticiaRepository;
        this.miembroService = miembroService;
    }

    @Transactional
    public News createNews(NewsDTO dto) {
        if (dto.getAuthorId() == null) {
            throw new RuntimeException("El id del autor es obligatorio");
        }

        Member autor = miembroService.getById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        News noticia = new News();
        mapDtoToEntity(dto, noticia, autor);

        if (noticia.getFechaPublicacion() == null) {
            noticia.setFechaPublicacion(LocalDate.now());
        }

        return noticiaRepository.save(noticia);
    }

    @Transactional(readOnly = true)
    public List<News> getAll() {
        return noticiaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public News getById(Long id) {
        return noticiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));
    }

    @Transactional(readOnly = true)
    public List<News> getLatestNews() {
        return noticiaRepository.findTop10ByOrderByFechaPublicacionDesc();
    }

    @Transactional(readOnly = true)
    public List<News> searchByTitle(String titulo) {
        return noticiaRepository.findByTituloContainingIgnoreCase(titulo);
    }

    @Transactional(readOnly = true)
    public List<News> searchByContent(String texto) {
        return noticiaRepository.findByContenidoContainingIgnoreCase(texto);
    }

    @Transactional(readOnly = true)
    public List<News> getByDate(LocalDate fecha) {
        return noticiaRepository.findByFechaPublicacion(fecha);
    }

    @Transactional
    public News updateNews(Long noticiaId, NewsDTO dto) {
        News noticia = getById(noticiaId);

        Member autor = noticia.getAutor();
        if (dto.getAuthorId() != null && !dto.getAuthorId().equals(noticia.getAutor().getId())) {
            autor = miembroService.getById(dto.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));
        }

        mapDtoToEntity(dto, noticia, autor);

        if (dto.getPublicationDate() == null && noticia.getFechaPublicacion() == null) {
            noticia.setFechaPublicacion(LocalDate.now());
        }

        return noticiaRepository.save(noticia);
    }

    @Transactional
    public void deleteNews(Long noticiaId) {
        News noticia = getById(noticiaId);
        noticiaRepository.delete(noticia);
    }

    private void mapDtoToEntity(NewsDTO dto, News noticia, Member autor) {
        noticia.setTitulo(dto.getTitle());
        noticia.setLead(dto.getLead());
        noticia.setContenido(dto.getContent());
        noticia.setFechaPublicacion(dto.getPublicationDate() != null ? dto.getPublicationDate() : noticia.getFechaPublicacion());
        noticia.setQue(dto.getWhat());
        noticia.setCuando(dto.getWhen());
        noticia.setDonde(dto.getWhere());
        noticia.setPorQue(dto.getWhy());
        noticia.setComo(dto.getHow());
        noticia.setAutor(autor);
    }
}