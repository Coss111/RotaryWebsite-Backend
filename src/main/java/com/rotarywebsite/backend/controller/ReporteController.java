package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.model.Reporte;
import com.rotarywebsite.backend.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    // Obtener todos los reportes
    @GetMapping
    public ResponseEntity<List<Reporte>> obtenerTodos() {
        List<Reporte> reportes = reporteService.obtenerTodos();
        return ResponseEntity.ok(reportes);
    }

    // Obtener reporte por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Reporte reporte = reporteService.obtenerPorId(id);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Generar reporte de membresías
    @PostMapping("/membresias")
    public ResponseEntity<?> generarReporteMembresias(@RequestBody Map<String, Long> request) {
        try {
            Long generadoPorId = request.get("generadoPorId");
            Reporte reporte = reporteService.generarReporteMembresias(generadoPorId);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Generar reporte de proyectos
    @PostMapping("/proyectos")
    public ResponseEntity<?> generarReporteProyectos(@RequestBody Map<String, Long> request) {
        try {
            Long generadoPorId = request.get("generadoPorId");
            Reporte reporte = reporteService.generarReporteProyectos(generadoPorId);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener últimos reportes
    @GetMapping("/ultimos")
    public ResponseEntity<List<Reporte>> obtenerUltimosReportes() {
        List<Reporte> reportes = reporteService.obtenerUltimosReportes();
        return ResponseEntity.ok(reportes);
    }

    // Eliminar reporte
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReporte(@PathVariable Long id) {
        try {
            reporteService.eliminarReporte(id);
            return ResponseEntity.ok(Map.of("mensaje", "Reporte eliminado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}