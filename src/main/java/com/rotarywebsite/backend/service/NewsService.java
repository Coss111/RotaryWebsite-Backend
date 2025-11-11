package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.model.News;
import com.rotarywebsite.backend.model.Member;
import com.rotarywebsite.backend.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository noticiaRepository;

    @Autowired
    private MemberService miembroService;

    // Crear nueva noticia
    public News createNews(String titulo, String lead, String contenido, 
                               Long autorId, String que, String cuando, 
                               String donde, String porQue, String como) {
        Member autor = miembroService.getById(autorId)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        News noticia = new News(titulo, lead, autor);
        noticia.setContenido(contenido);
        noticia.setQue(que);
        noticia.setCuando(cuando);
        noticia.setDonde(donde);
        noticia.setPorQue(porQue);
        noticia.setComo(como);

        return noticiaRepository.save(noticia);
    }

    // Obtener todas las noticias
    public List<News> getAll() {
        return noticiaRepository.findAll();
    }

    // Obtener noticia por ID
    public News getById(Long id) {
        return noticiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));
    }

    // Obtener últimas noticias
    public List<News> getLatestNews() {
        return noticiaRepository.findTop10ByOrderByFechaPublicacionDesc();
    }

    // Buscar noticias por título
    public List<News> searchByTitle(String titulo) {
        return noticiaRepository.findByTituloContainingIgnoreCase(titulo);
    }

    // Buscar noticias por contenido
    public List<News> searchByContent(String texto) {
        return noticiaRepository.findByLeadContainingIgnoreCase(texto);
    }

    // Obtener noticias por fecha
    public List<News> getByDate(LocalDate fecha) {
        return noticiaRepository.findByFechaPublicacion(fecha);
    }

    // Actualizar noticia
    public News updateNews(Long noticiaId, News noticiaActualizada) {
        News noticia = getById(noticiaId);
        
        noticia.setTitulo(noticiaActualizada.getTitulo());
        noticia.setLead(noticiaActualizada.getLead());
        noticia.setContenido(noticiaActualizada.getContenido());
        noticia.setQue(noticiaActualizada.getQue());
        noticia.setCuando(noticiaActualizada.getCuando());
        noticia.setDonde(noticiaActualizada.getDonde());
        noticia.setPorQue(noticiaActualizada.getPorQue());
        noticia.setComo(noticiaActualizada.getComo());

        return noticiaRepository.save(noticia);
    }

    // Eliminar noticia
    public void deleteNews(Long noticiaId) {
        News noticia = getById(noticiaId);
        noticiaRepository.delete(noticia);
    }
}