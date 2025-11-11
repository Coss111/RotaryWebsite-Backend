package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.dto.ProjectDTO;
import com.rotarywebsite.backend.model.Project;
import com.rotarywebsite.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Obtener todos los proyectos
    @GetMapping
    public ResponseEntity<List<Project>> getAll() {
        List<Project> projects = projectService.getAll();
        return ResponseEntity.ok(projects);
    }

    // Obtener proyecto por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Project project = projectService.getById(id);
            return ResponseEntity.ok(project);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Crear nuevo proyecto usando DTO
    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectDTO projectDTO) {
        try {
            Project project = projectService.createProject(
                projectDTO.getName(),
                projectDTO.getDescription(),
                projectDTO.getCreatorId()
            );
            
            // Actualizar campos adicionales del DTO
            if (projectDTO.getLocation() != null) project.setLugar(projectDTO.getLocation());
            if (projectDTO.getBudget() != null) project.setPresupuesto(projectDTO.getBudget());
            if (projectDTO.getSocialImpact() != null) project.setImpactoSocial(projectDTO.getSocialImpact());
            
            Project savedProject = projectService.updateProject(project.getId(), project);
            return ResponseEntity.ok(savedProject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Actualizar proyecto
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody Project project) {
        try {
            Project updatedProject = projectService.updateProject(id, project);
            return ResponseEntity.ok(updatedProject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Cambiar estado de proyecto
    @PutMapping("/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            String statusStr = request.get("status");
            com.rotarywebsite.backend.model.ProjectStatus status = 
                com.rotarywebsite.backend.model.ProjectStatus.valueOf(statusStr);
            
            Project project = projectService.changeStatus(id, status);
            return ResponseEntity.ok(project);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Buscar proyectos por nombre
    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchByName(@RequestParam String name) {
        List<Project> projects = projectService.searchByName(name);
        return ResponseEntity.ok(projects);
    }

    // Obtener proyectos activos
    @GetMapping("/active")
    public ResponseEntity<List<Project>> getActiveProjects() {
        List<Project> projects = projectService.getActiveProjects();
        return ResponseEntity.ok(projects);
    }
}