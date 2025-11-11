package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.Noticia;
import com.rotarywebsite.backend.model.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
    
    // Buscar noticias por título
    List<Noticia> findByTituloContainingIgnoreCase(String titulo);
    
    // Buscar noticias por autor
    List<Noticia> findByAutor(Miembro autor);
    
    // Buscar noticias por fecha de publicación
    List<Noticia> findByFechaPublicacion(LocalDate fecha);
    
    // Buscar noticias entre fechas
    List<Noticia> findByFechaPublicacionBetween(LocalDate inicio, LocalDate fin);
    
    // Buscar últimas noticias ordenadas por fecha
    List<Noticia> findTop10ByOrderByFechaPublicacionDesc();
    
    // Buscar noticias por contenido del lead
    List<Noticia> findByLeadContainingIgnoreCase(String texto);
}