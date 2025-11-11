package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.News;
import com.rotarywebsite.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    
    // Buscar noticias por título
    List<News> findByTituloContainingIgnoreCase(String titulo);
    
    // Buscar noticias por autor
    List<News> findByAutor(Member autor);
    
    // Buscar noticias por fecha de publicación
    List<News> findByFechaPublicacion(LocalDate fecha);
    
    // Buscar noticias entre fechas
    List<News> findByFechaPublicacionBetween(LocalDate inicio, LocalDate fin);
    
    // Buscar últimas noticias ordenadas por fecha
    List<News> findTop10ByOrderByFechaPublicacionDesc();
    
    // Buscar noticias por contenido del lead
    List<News> findByLeadContainingIgnoreCase(String texto);
}