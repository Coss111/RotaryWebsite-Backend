package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.model.Report;
import com.rotarywebsite.backend.model.Member;
import com.rotarywebsite.backend.model.ReportType;
import com.rotarywebsite.backend.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reporteRepository;

    @Autowired
    private MemberService miembroService;

    @Autowired
    private ProjectService proyectoService;

    @Autowired
    private MemberService miembrosService;

    // Generar reporte de membresías
    public Report generarReporteMembresias(Long generadoPorId) {
        Member generadoPor = miembroService.obtenerPorId(generadoPorId)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        // Lógica para generar reporte
        long totalMiembros = miembrosService.contarPorEstado(com.rotarywebsite.backend.model.MembershipStatus.ACTIVE);
        long pendientesRenovacion = miembrosService.contarPorEstado(com.rotarywebsite.backend.model.MembershipStatus.PENDING_RENEWAL);

        String contenido = String.format(
            "{\"totalMiembros\": %d, \"pendientesRenovacion\": %d, \"fechaGeneracion\": \"%s\"}",
            totalMiembros, pendientesRenovacion, LocalDateTime.now()
        );

        Report reporte = new Report("Reporte de Membresías", ReportType.MEMBERSHIPS, generadoPor);
        reporte.setContenido(contenido);

        return reporteRepository.save(reporte);
    }

    // Generar reporte de proyectos
    public Report generarReporteProyectos(Long generadoPorId) {
        Member generadoPor = miembroService.obtenerPorId(generadoPorId)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        // Aquí iría la lógica para generar el reporte de proyectos
        String contenido = "{\"reporte\": \"Proyectos en ejecución\", \"detalles\": \"...\"}";

        Report reporte = new Report("Reporte de Proyectos", ReportType.PROJECTS, generadoPor);
        reporte.setContenido(contenido);

        return reporteRepository.save(reporte);
    }

    // Obtener todos los reportes
    public List<Report> obtenerTodos() {
        return reporteRepository.findAll();
    }

    // Obtener reporte por ID
    public Report obtenerPorId(Long id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
    }

    // Obtener reportes por tipo
    public List<Report> obtenerPorTipo(ReportType tipo) {
        return reporteRepository.findByTipo(tipo);
    }

    // Obtener últimos reportes
    public List<Report> obtenerUltimosReportes() {
        return reporteRepository.findTop5ByOrderByFechaGeneracionDesc();
    }

    // Eliminar reporte
    public void eliminarReporte(Long reporteId) {
        Report reporte = obtenerPorId(reporteId);
        reporteRepository.delete(reporte);
    }
}