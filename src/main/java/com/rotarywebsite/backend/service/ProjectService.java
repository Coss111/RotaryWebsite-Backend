package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.model.Project;
import com.rotarywebsite.backend.model.Member;
import com.rotarywebsite.backend.model.ProjectStatus;
import com.rotarywebsite.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository proyectoRepository;

    @Autowired
    private MemberService miembroService;

    // Crear nuevo proyecto
    public Project crearProyecto(String nombre, String descripcion, Long creadorId) {
        Member creador = miembroService.obtenerPorId(creadorId)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        Project proyecto = new Project(nombre, descripcion, creador);
        return proyectoRepository.save(proyecto);
    }

    // Obtener todos los proyectos
    public List<Project> obtenerTodos() {
        return proyectoRepository.findAll();
    }

    // Obtener proyecto por ID
    public Project obtenerPorId(Long id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }

    // Buscar proyectos por nombre
    public List<Project> buscarPorNombre(String nombre) {
        return proyectoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Obtener proyectos por estado
    public List<Project> obtenerPorEstado(ProjectStatus estado) {
        return proyectoRepository.findByEstado(estado);
    }

    // Cambiar estado de proyecto
    public Project cambiarEstado(Long proyectoId, ProjectStatus nuevoEstado) {
        Project proyecto = obtenerPorId(proyectoId);
        proyecto.setEstado(nuevoEstado);
        return proyectoRepository.save(proyecto);
    }

    // Obtener proyectos activos
    public List<Project> obtenerProyectosActivos() {
        List<ProjectStatus> estadosInactivos = List.of(
            ProjectStatus.COMPLETED, 
            ProjectStatus.CANCELLED
        );
        return proyectoRepository.findByEstadoNotIn(estadosInactivos);
    }

    // Actualizar proyecto
    public Project actualizarProyecto(Long proyectoId, Project proyectoActualizado) {
        Project proyecto = obtenerPorId(proyectoId);
        
        proyecto.setNombre(proyectoActualizado.getNombre());
        proyecto.setDescripcion(proyectoActualizado.getDescripcion());
        proyecto.setLugar(proyectoActualizado.getLugar());
        proyecto.setImpactoSocial(proyectoActualizado.getImpactoSocial());
        proyecto.setPresupuesto(proyectoActualizado.getPresupuesto());
        
        return proyectoRepository.save(proyecto);
    }

    // Contar proyectos por estado
    public long contarPorEstado(ProjectStatus estado) {
        return proyectoRepository.countByEstado(estado);
    }
}