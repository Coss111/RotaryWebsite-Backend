package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.dto.ProjectDTO;
import com.rotarywebsite.backend.dto.ProjectMapDTO;
import com.rotarywebsite.backend.model.Project;
import com.rotarywebsite.backend.model.ProjectStatus;
import com.rotarywebsite.backend.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/map")
    public ResponseEntity<List<ProjectMapDTO>> getProjectsForMap() {
        List<ProjectMapDTO> projects = projectService.getProjectsForMap()
                .stream()
                .map(this::toMapDto)
                .toList();

        return ResponseEntity.ok(projects);
    }

    @GetMapping("/map/location")
    public ResponseEntity<?> getProjectsForMapByPlace(@RequestParam String place) {
        try {
            List<ProjectMapDTO> projects = projectService.getProjectsForMapByPlace(place)
                    .stream()
                    .map(this::toMapDto)
                    .toList();

            return ResponseEntity.ok(projects);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/map/nearby")
    public ResponseEntity<?> getNearbyProjects(@RequestParam Double lat,
                                               @RequestParam Double lng,
                                               @RequestParam Double radiusKm) {
        try {
            List<ProjectMapDTO> projects = projectService.getNearbyProjects(lat, lng, radiusKm)
                    .stream()
                    .map(this::toMapDto)
                    .toList();

            return ResponseEntity.ok(projects);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAll() {
        List<ProjectDTO> projects = projectService.getAll()
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Project project = projectService.getById(id);
            return ResponseEntity.ok(toDto(project));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectDTO projectDTO) {
        try {
            Project project = projectService.createProject(projectDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDto(project));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody ProjectDTO dto) {
        try {
            Project updatedProject = projectService.updateProject(id, dto);
            return ResponseEntity.ok(toDto(updatedProject));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            String statusStr = request.get("status");
            if (statusStr == null || statusStr.isBlank()) {
                throw new RuntimeException("El campo status es obligatorio");
            }

            ProjectStatus status = ProjectStatus.valueOf(statusStr.toUpperCase());
            Project project = projectService.changeStatus(id, status);

            return ResponseEntity.ok(toDto(project));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Estado de proyecto inválido"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.ok(Map.of("message", "Proyecto eliminado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProjectDTO>> searchByName(@RequestParam String name) {
        List<ProjectDTO> projects = projectService.searchByName(name)
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(projects);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ProjectDTO>> getActiveProjects() {
        List<ProjectDTO> projects = projectService.getActiveProjects()
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(projects);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getByStatus(@PathVariable String status) {
        try {
            ProjectStatus projectStatus = ProjectStatus.valueOf(status.toUpperCase());

            List<ProjectDTO> projects = projectService.getByStatus(projectStatus)
                    .stream()
                    .map(this::toDto)
                    .toList();

            return ResponseEntity.ok(projects);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Estado de proyecto inválido"));
        }
    }

    private ProjectDTO toDto(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getNombre());
        dto.setDescription(project.getDescripcion());
        dto.setStatus(project.getEstado());
        dto.setStartDate(project.getFechaInicio());
        dto.setEndDate(project.getFechaFin());
        dto.setSocialImpact(project.getImpactoSocial());
        dto.setBudget(project.getPresupuesto());
        dto.setLocation(project.getLugar());
        dto.setBeneficiaryCount(project.getNumBeneficiarios());
        dto.setResponsiblePerson(project.getResponsable());
        dto.setNewGenerationsParticipation(project.isParticipanNuevasGeneraciones());
        dto.setOtherClubsParticipation(project.isParticipanOtrosClubes());
        dto.setParticipatingInstitution(project.getInstitucionParticipante());
        dto.setMyRotaryUrl(project.getUrlMyRotary());
        dto.setLatitude(project.getLatitud());
        dto.setLongitude(project.getLongitud());

        if (project.getCreador() != null) {
            dto.setCreatorId(project.getCreador().getId());
            dto.setCreatorName(project.getCreador().getNombre());
        }

        return dto;
    }

    private ProjectMapDTO toMapDto(Project project) {
        ProjectMapDTO dto = new ProjectMapDTO();
        dto.setId(project.getId());
        dto.setName(project.getNombre());
        dto.setStatus(project.getEstado());
        dto.setLocation(project.getLugar());
        dto.setSocialImpact(project.getImpactoSocial());
        dto.setLatitude(project.getLatitud());
        dto.setLongitude(project.getLongitud());

        if (project.getCreador() != null) {
            dto.setCreatorId(project.getCreador().getId());
            dto.setCreatorName(project.getCreador().getNombre());
        }

        return dto;
    }
}