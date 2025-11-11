package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.model.Report;
import com.rotarywebsite.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ReportService reporteService;

    // Obtener todos los reportes
    @GetMapping
    public ResponseEntity<List<Report>> obtenerTodos() {
        List<Report> reportes = reporteService.obtenerTodos();
        return ResponseEntity.ok(reportes);
    }

    // Obtener reporte por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Report reporte = reporteService.obtenerPorId(id);
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
            Report reporte = reporteService.generarReporteMembresias(generadoPorId);
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
            Report reporte = reporteService.generarReporteProyectos(generadoPorId);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener últimos reportes
    @GetMapping("/ultimos")
    public ResponseEntity<List<Report>> obtenerUltimosReportes() {
        List<Report> reportes = reporteService.obtenerUltimosReportes();
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