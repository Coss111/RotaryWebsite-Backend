package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.model.Proyecto;
import com.rotarywebsite.backend.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/proyectos")
@CrossOrigin(origins = "*")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    // Obtener todos los proyectos
    @GetMapping
    public ResponseEntity<List<Proyecto>> obtenerTodos() {
        List<Proyecto> proyectos = proyectoService.obtenerTodos();
        return ResponseEntity.ok(proyectos);
    }

    // Obtener proyecto por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Proyecto proyecto = proyectoService.obtenerPorId(id);
            return ResponseEntity.ok(proyecto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Crear nuevo proyecto
    @PostMapping
    public ResponseEntity<?> crearProyecto(@RequestBody Map<String, Object> request) {
        try {
            String nombre = (String) request.get("nombre");
            String descripcion = (String) request.get("descripcion");
            Long creadorId = Long.valueOf(request.get("creadorId").toString());

            Proyecto proyecto = proyectoService.crearProyecto(nombre, descripcion, creadorId);
            return ResponseEntity.ok(proyecto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Actualizar proyecto
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProyecto(@PathVariable Long id, @RequestBody Proyecto proyectoActualizado) {
        try {
            Proyecto proyecto = proyectoService.actualizarProyecto(id, proyectoActualizado);
            return ResponseEntity.ok(proyecto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Cambiar estado de proyecto
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            String estadoStr = request.get("estado");
            com.rotarywebsite.backend.model.EstadoProyecto estado = 
                com.rotarywebsite.backend.model.EstadoProyecto.valueOf(estadoStr);
            
            Proyecto proyecto = proyectoService.cambiarEstado(id, estado);
            return ResponseEntity.ok(proyecto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Buscar proyectos por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<Proyecto>> buscarPorNombre(@RequestParam String nombre) {
        List<Proyecto> proyectos = proyectoService.buscarPorNombre(nombre);
        return ResponseEntity.ok(proyectos);
    }

    // Obtener proyectos activos
    @GetMapping("/activos")
    public ResponseEntity<List<Proyecto>> obtenerProyectosActivos() {
        List<Proyecto> proyectos = proyectoService.obtenerProyectosActivos();
        return ResponseEntity.ok(proyectos);
    }

    // Obtener estadísticas de proyectos
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Long>> obtenerEstadisticas() {
        Map<String, Long> estadisticas = Map.of(
            "planificacion", proyectoService.contarPorEstado(com.rotarywebsite.backend.model.EstadoProyecto.PLANIFICACION),
            "enProgreso", proyectoService.contarPorEstado(com.rotarywebsite.backend.model.EstadoProyecto.EN_PROGRESO),
            "completados", proyectoService.contarPorEstado(com.rotarywebsite.backend.model.EstadoProyecto.COMPLETADO)
        );
        return ResponseEntity.ok(estadisticas);
    }
}