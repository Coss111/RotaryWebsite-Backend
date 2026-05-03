package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.Project;
import com.rotarywebsite.backend.model.ProjectStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Override
    @EntityGraph(attributePaths = {"creador"})
    List<Project> findAll();

    @Override
    @EntityGraph(attributePaths = {"creador"})
    Optional<Project> findById(Long id);

    @EntityGraph(attributePaths = {"creador"})
    List<Project> findByNombreContainingIgnoreCase(String nombre);

    @EntityGraph(attributePaths = {"creador"})
    List<Project> findByEstado(ProjectStatus estado);

    @EntityGraph(attributePaths = {"creador"})
    List<Project> findByCreadorId(Long creadorId);

    @EntityGraph(attributePaths = {"creador"})
    List<Project> findByEstadoNotIn(List<ProjectStatus> estados);

    @EntityGraph(attributePaths = {"creador"})
    List<Project> findByLugarContainingIgnoreCase(String lugar);

    @EntityGraph(attributePaths = {"creador"})
    List<Project> findByLatitudIsNotNullAndLongitudIsNotNull();

    @EntityGraph(attributePaths = {"creador"})
    List<Project> findByLugarContainingIgnoreCaseAndLatitudIsNotNullAndLongitudIsNotNull(String lugar);

    long countByEstado(ProjectStatus estado);
}