package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.News;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Override
    @EntityGraph(attributePaths = {"autor"})
    List<News> findAll();

    @Override
    @EntityGraph(attributePaths = {"autor"})
    Optional<News> findById(Long id);

    @EntityGraph(attributePaths = {"autor"})
    List<News> findByTituloContainingIgnoreCase(String titulo);

    @EntityGraph(attributePaths = {"autor"})
    List<News> findByAutorId(Long autorId);

    @EntityGraph(attributePaths = {"autor"})
    List<News> findByFechaPublicacion(LocalDate fecha);

    @EntityGraph(attributePaths = {"autor"})
    List<News> findByFechaPublicacionBetween(LocalDate inicio, LocalDate fin);

    @EntityGraph(attributePaths = {"autor"})
    List<News> findTop10ByOrderByFechaPublicacionDesc();

    @EntityGraph(attributePaths = {"autor"})
    List<News> findByLeadContainingIgnoreCase(String texto);

    @EntityGraph(attributePaths = {"autor"})
    List<News> findByContenidoContainingIgnoreCase(String texto);
}