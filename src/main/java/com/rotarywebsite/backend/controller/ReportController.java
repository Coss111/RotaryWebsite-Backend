package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.dto.GenerateReportRequestDTO;
import com.rotarywebsite.backend.dto.ReportDTO;
import com.rotarywebsite.backend.model.Report;
import com.rotarywebsite.backend.model.ReportType;
import com.rotarywebsite.backend.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
public class ReportController {

    private final ReportService reporteService;

    public ReportController(ReportService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    public ResponseEntity<List<ReportDTO>> obtenerTodos() {
        List<ReportDTO> reportes = reporteService.getAll()
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(reportes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Report reporte = reporteService.getById(id);
            return ResponseEntity.ok(toDto(reporte));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/tipo/{type}")
    public ResponseEntity<?> obtenerPorTipo(@PathVariable String type) {
        try {
            ReportType reportType = ReportType.valueOf(type.toUpperCase());

            List<ReportDTO> reportes = reporteService.getByType(reportType)
                    .stream()
                    .map(this::toDto)
                    .toList();

            return ResponseEntity.ok(reportes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Tipo de reporte inválido"));
        }
    }

    @PostMapping("/membresias")
    public ResponseEntity<?> generarReporteMembresias(@RequestBody GenerateReportRequestDTO request) {
        try {
            if (request.getGeneratedById() == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "generatedById es obligatorio"));
            }

            Report reporte = reporteService.generateMembershipReport(request.getGeneratedById());
            return ResponseEntity.status(HttpStatus.CREATED).body(toDto(reporte));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/proyectos")
    public ResponseEntity<?> generarReporteProyectos(@RequestBody GenerateReportRequestDTO request) {
        try {
            if (request.getGeneratedById() == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "generatedById es obligatorio"));
            }

            Report reporte = reporteService.generateProjectReport(request.getGeneratedById());
            return ResponseEntity.status(HttpStatus.CREATED).body(toDto(reporte));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/ultimos")
    public ResponseEntity<List<ReportDTO>> obtenerUltimosReportes() {
        List<ReportDTO> reportes = reporteService.getLatestReports()
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(reportes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReporte(@PathVariable Long id) {
        try {
            reporteService.deleteReport(id);
            return ResponseEntity.ok(Map.of("mensaje", "Reporte eliminado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    private ReportDTO toDto(Report reporte) {
        ReportDTO dto = new ReportDTO();
        dto.setId(reporte.getId());
        dto.setName(reporte.getNombre());
        dto.setType(reporte.getTipo());
        dto.setContent(reporte.getContenido());
        dto.setFileUrl(reporte.getUrlArchivo());
        dto.setGeneratedAt(reporte.getFechaGeneracion());

        if (reporte.getGeneradoPor() != null) {
            dto.setGeneratedById(reporte.getGeneradoPor().getId());
            dto.setGeneratedByName(reporte.getGeneradoPor().getNombre());
        }

        return dto;
    }
}