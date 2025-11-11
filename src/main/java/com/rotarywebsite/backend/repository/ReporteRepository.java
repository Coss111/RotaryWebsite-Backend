package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.Reporte;
import com.rotarywebsite.backend.model.TipoReporte;
import com.rotarywebsite.backend.model.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    
    // Buscar reportes por tipo
    List<Reporte> findByTipo(TipoReporte tipo);
    
    // Buscar reportes por quien los generó
    List<Reporte> findByGeneradoPor(Miembro generadoPor);
    
    // Buscar reportes entre fechas
    List<Reporte> findByFechaGeneracionBetween(LocalDateTime inicio, LocalDateTime fin);
    
    // Buscar últimos reportes
    List<Reporte> findTop5ByOrderByFechaGeneracionDesc();
    
    // Contar reportes por tipo
    long countByTipo(TipoReporte tipo);
}