package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.Report;
import com.rotarywebsite.backend.model.ReportType;
import com.rotarywebsite.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    
    // Buscar reportes por tipo
    List<Report> findByTipo(ReportType tipo);
    
    // Buscar reportes por quien los generó
    List<Report> findByGeneradoPor(Member generadoPor);
    
    // Buscar reportes entre fechas
    List<Report> findByFechaGeneracionBetween(LocalDateTime inicio, LocalDateTime fin);
    
    // Buscar últimos reportes
    List<Report> findTop5ByOrderByFechaGeneracionDesc();
    
    // Contar reportes por tipo
    long countByTipo(ReportType tipo);
}