package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.Project;
import com.rotarywebsite.backend.model.ProjectStatus;
import com.rotarywebsite.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    // Buscar proyectos por nombre
    List<Project> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar proyectos por estado
    List<Project> findByEstado(ProjectStatus estado);
    
    // Buscar proyectos por creador
    List<Project> findByCreador(Member creador);
    
    // Buscar proyectos activos (no completados ni cancelados)
    List<Project> findByEstadoNotIn(List<ProjectStatus> estados);
    
    // Buscar proyectos por lugar
    List<Project> findByLugarContainingIgnoreCase(String lugar);
    
    // Contar proyectos por estado
    long countByEstado(ProjectStatus estado);
}