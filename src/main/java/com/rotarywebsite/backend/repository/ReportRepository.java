package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.Report;
import com.rotarywebsite.backend.model.ReportType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Override
    @EntityGraph(attributePaths = {"generadoPor"})
    List<Report> findAll();

    @Override
    @EntityGraph(attributePaths = {"generadoPor"})
    Optional<Report> findById(Long id);

    @EntityGraph(attributePaths = {"generadoPor"})
    List<Report> findByTipo(ReportType tipo);

    @EntityGraph(attributePaths = {"generadoPor"})
    List<Report> findByGeneradoPorId(Long generadoPorId);

    @EntityGraph(attributePaths = {"generadoPor"})
    List<Report> findByFechaGeneracionBetween(LocalDateTime inicio, LocalDateTime fin);

    @EntityGraph(attributePaths = {"generadoPor"})
    List<Report> findTop5ByOrderByFechaGeneracionDesc();

    long countByTipo(ReportType tipo);
}