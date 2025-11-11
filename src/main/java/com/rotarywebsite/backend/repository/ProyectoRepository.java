package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.Proyecto;
import com.rotarywebsite.backend.model.EstadoProyecto;
import com.rotarywebsite.backend.model.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    
    // Buscar proyectos por nombre
    List<Proyecto> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar proyectos por estado
    List<Proyecto> findByEstado(EstadoProyecto estado);
    
    // Buscar proyectos por creador
    List<Proyecto> findByCreador(Miembro creador);
    
    // Buscar proyectos activos (no completados ni cancelados)
    List<Proyecto> findByEstadoNotIn(List<EstadoProyecto> estados);
    
    // Buscar proyectos por lugar
    List<Proyecto> findByLugarContainingIgnoreCase(String lugar);
    
    // Contar proyectos por estado
    long countByEstado(EstadoProyecto estado);
}