package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.model.Proyecto;
import com.rotarywebsite.backend.model.Miembro;
import com.rotarywebsite.backend.model.EstadoProyecto;
import com.rotarywebsite.backend.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private MiembroService miembroService;

    // Crear nuevo proyecto
    public Proyecto crearProyecto(String nombre, String descripcion, Long creadorId) {
        Miembro creador = miembroService.obtenerPorId(creadorId)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        Proyecto proyecto = new Proyecto(nombre, descripcion, creador);
        return proyectoRepository.save(proyecto);
    }

    // Obtener todos los proyectos
    public List<Proyecto> obtenerTodos() {
        return proyectoRepository.findAll();
    }

    // Obtener proyecto por ID
    public Proyecto obtenerPorId(Long id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }

    // Buscar proyectos por nombre
    public List<Proyecto> buscarPorNombre(String nombre) {
        return proyectoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Obtener proyectos por estado
    public List<Proyecto> obtenerPorEstado(EstadoProyecto estado) {
        return proyectoRepository.findByEstado(estado);
    }

    // Cambiar estado de proyecto
    public Proyecto cambiarEstado(Long proyectoId, EstadoProyecto nuevoEstado) {
        Proyecto proyecto = obtenerPorId(proyectoId);
        proyecto.setEstado(nuevoEstado);
        return proyectoRepository.save(proyecto);
    }

    // Obtener proyectos activos
    public List<Proyecto> obtenerProyectosActivos() {
        List<EstadoProyecto> estadosInactivos = List.of(
            EstadoProyecto.COMPLETADO, 
            EstadoProyecto.CANCELADO
        );
        return proyectoRepository.findByEstadoNotIn(estadosInactivos);
    }

    // Actualizar proyecto
    public Proyecto actualizarProyecto(Long proyectoId, Proyecto proyectoActualizado) {
        Proyecto proyecto = obtenerPorId(proyectoId);
        
        proyecto.setNombre(proyectoActualizado.getNombre());
        proyecto.setDescripcion(proyectoActualizado.getDescripcion());
        proyecto.setLugar(proyectoActualizado.getLugar());
        proyecto.setImpactoSocial(proyectoActualizado.getImpactoSocial());
        proyecto.setPresupuesto(proyectoActualizado.getPresupuesto());
        
        return proyectoRepository.save(proyecto);
    }

    // Contar proyectos por estado
    public long contarPorEstado(EstadoProyecto estado) {
        return proyectoRepository.countByEstado(estado);
    }
}