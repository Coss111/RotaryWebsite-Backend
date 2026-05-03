package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.dto.ProjectDTO;
import com.rotarywebsite.backend.model.Member;
import com.rotarywebsite.backend.model.Project;
import com.rotarywebsite.backend.model.ProjectStatus;
import com.rotarywebsite.backend.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository proyectoRepository;
    private final MemberService miembroService;

    public ProjectService(ProjectRepository proyectoRepository, MemberService miembroService) {
        this.proyectoRepository = proyectoRepository;
        this.miembroService = miembroService;
    }

    @Transactional
    public Project createProject(ProjectDTO dto) {
        validateProjectDates(dto);
        validateCoordinates(dto.getLatitude(), dto.getLongitude());

        if (dto.getCreatorId() == null) {
            throw new RuntimeException("El id del creador es obligatorio");
        }

        Member creador = miembroService.getById(dto.getCreatorId())
                .orElseThrow(() -> new RuntimeException("El miembro creador no existe"));

        Project proyecto = new Project();
        mapDtoToEntity(dto, proyecto, creador);

        if (proyecto.getEstado() == null) {
            proyecto.setEstado(ProjectStatus.PLANNING);
        }

        return proyectoRepository.save(proyecto);
    }

    @Transactional(readOnly = true)
    public List<Project> getAll() {
        return proyectoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Project getById(Long id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Project> searchByName(String nombre) {
        return proyectoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Transactional(readOnly = true)
    public List<Project> getByStatus(ProjectStatus estado) {
        return proyectoRepository.findByEstado(estado);
    }

    @Transactional
    public Project changeStatus(Long proyectoId, ProjectStatus nuevoEstado) {
        Project proyecto = getById(proyectoId);
        proyecto.setEstado(nuevoEstado);
        return proyectoRepository.save(proyecto);
    }

    @Transactional(readOnly = true)
    public List<Project> getActiveProjects() {
        List<ProjectStatus> estadosInactivos = List.of(
                ProjectStatus.COMPLETED,
                ProjectStatus.CANCELLED
        );
        return proyectoRepository.findByEstadoNotIn(estadosInactivos);
    }

    @Transactional
    public Project updateProject(Long proyectoId, ProjectDTO dto) {
        validateProjectDates(dto);
        validateCoordinates(dto.getLatitude(), dto.getLongitude());

        Project proyecto = getById(proyectoId);

        Member creador = proyecto.getCreador();
        if (dto.getCreatorId() != null && !dto.getCreatorId().equals(proyecto.getCreador().getId())) {
            creador = miembroService.getById(dto.getCreatorId())
                    .orElseThrow(() -> new RuntimeException("El miembro creador no existe"));
        }

        mapDtoToEntity(dto, proyecto, creador);
        return proyectoRepository.save(proyecto);
    }

    @Transactional
    public void deleteProject(Long proyectoId) {
        Project proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        proyectoRepository.delete(proyecto);
    }

    @Transactional(readOnly = true)
    public long countByStatus(ProjectStatus estado) {
        return proyectoRepository.countByEstado(estado);
    }

    @Transactional(readOnly = true)
    public List<Project> getProjectsForMap() {
        return proyectoRepository.findByLatitudIsNotNullAndLongitudIsNotNull();
    }

    @Transactional(readOnly = true)
    public List<Project> getProjectsForMapByPlace(String place) {
        if (place == null || place.isBlank()) {
            throw new RuntimeException("El parámetro place es obligatorio");
        }
        return proyectoRepository.findByLugarContainingIgnoreCaseAndLatitudIsNotNullAndLongitudIsNotNull(place);
    }

    @Transactional(readOnly = true)
    public List<Project> getNearbyProjects(Double lat, Double lng, Double radiusKm) {
        if (lat == null || lng == null || radiusKm == null) {
            throw new RuntimeException("lat, lng y radiusKm son obligatorios");
        }

        if (radiusKm <= 0) {
            throw new RuntimeException("radiusKm debe ser mayor a 0");
        }

        List<Project> projectsWithCoordinates = proyectoRepository.findByLatitudIsNotNullAndLongitudIsNotNull();

        return projectsWithCoordinates.stream()
                .filter(project ->
                        distanceKm(lat, lng, project.getLatitud(), project.getLongitud()) <= radiusKm
                )
                .toList();
    }

    private void validateProjectDates(ProjectDTO dto) {
        if (dto.getStartDate() != null && dto.getEndDate() != null
                && dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new RuntimeException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }
    }

    private void validateCoordinates(Double latitude, Double longitude) {
        boolean latitudePresent = latitude != null;
        boolean longitudePresent = longitude != null;

        if (latitudePresent ^ longitudePresent) {
            throw new RuntimeException("Latitud y longitud deben enviarse juntas");
        }

        if (latitude != null && (latitude < -90 || latitude > 90)) {
            throw new RuntimeException("La latitud debe estar entre -90 y 90");
        }

        if (longitude != null && (longitude < -180 || longitude > 180)) {
            throw new RuntimeException("La longitud debe estar entre -180 y 180");
        }
    }

    private void mapDtoToEntity(ProjectDTO dto, Project proyecto, Member creador) {
        proyecto.setNombre(dto.getName());
        proyecto.setDescripcion(dto.getDescription());
        proyecto.setEstado(dto.getStatus() != null ? dto.getStatus() : ProjectStatus.PLANNING);
        proyecto.setFechaInicio(dto.getStartDate());
        proyecto.setFechaFin(dto.getEndDate());
        proyecto.setImpactoSocial(dto.getSocialImpact());
        proyecto.setPresupuesto(dto.getBudget());
        proyecto.setLugar(dto.getLocation());
        proyecto.setNumBeneficiarios(dto.getBeneficiaryCount());
        proyecto.setResponsable(dto.getResponsiblePerson());
        proyecto.setParticipanNuevasGeneraciones(Boolean.TRUE.equals(dto.getNewGenerationsParticipation()));
        proyecto.setParticipanOtrosClubes(Boolean.TRUE.equals(dto.getOtherClubsParticipation()));
        proyecto.setInstitucionParticipante(dto.getParticipatingInstitution());
        proyecto.setUrlMyRotary(dto.getMyRotaryUrl());
        proyecto.setLatitud(dto.getLatitude());
        proyecto.setLongitud(dto.getLongitude());
        proyecto.setCreador(creador);
    }

    private double distanceKm(double lat1, double lon1, double lat2, double lon2) {
        final double earthRadiusKm = 6371.0;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadiusKm * c;
    }
}