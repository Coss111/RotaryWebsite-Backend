package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.model.Reporte;
import com.rotarywebsite.backend.model.Miembro;
import com.rotarywebsite.backend.model.TipoReporte;
import com.rotarywebsite.backend.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private MiembroService miembroService;

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private MiembroService miembrosService;

    // Generar reporte de membresías
    public Reporte generarReporteMembresias(Long generadoPorId) {
        Miembro generadoPor = miembroService.obtenerPorId(generadoPorId)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        // Lógica para generar reporte
        long totalMiembros = miembrosService.contarPorEstado(com.rotarywebsite.backend.model.EstadoMembresia.ACTIVO);
        long pendientesRenovacion = miembrosService.contarPorEstado(com.rotarywebsite.backend.model.EstadoMembresia.PENDIENTE_RENOVACION);

        String contenido = String.format(
            "{\"totalMiembros\": %d, \"pendientesRenovacion\": %d, \"fechaGeneracion\": \"%s\"}",
            totalMiembros, pendientesRenovacion, LocalDateTime.now()
        );

        Reporte reporte = new Reporte("Reporte de Membresías", TipoReporte.MEMBRESIAS, generadoPor);
        reporte.setContenido(contenido);

        return reporteRepository.save(reporte);
    }

    // Generar reporte de proyectos
    public Reporte generarReporteProyectos(Long generadoPorId) {
        Miembro generadoPor = miembroService.obtenerPorId(generadoPorId)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        // Aquí iría la lógica para generar el reporte de proyectos
        String contenido = "{\"reporte\": \"Proyectos en ejecución\", \"detalles\": \"...\"}";

        Reporte reporte = new Reporte("Reporte de Proyectos", TipoReporte.PROYECTOS, generadoPor);
        reporte.setContenido(contenido);

        return reporteRepository.save(reporte);
    }

    // Obtener todos los reportes
    public List<Reporte> obtenerTodos() {
        return reporteRepository.findAll();
    }

    // Obtener reporte por ID
    public Reporte obtenerPorId(Long id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
    }

    // Obtener reportes por tipo
    public List<Reporte> obtenerPorTipo(TipoReporte tipo) {
        return reporteRepository.findByTipo(tipo);
    }

    // Obtener últimos reportes
    public List<Reporte> obtenerUltimosReportes() {
        return reporteRepository.findTop5ByOrderByFechaGeneracionDesc();
    }

    // Eliminar reporte
    public void eliminarReporte(Long reporteId) {
        Reporte reporte = obtenerPorId(reporteId);
        reporteRepository.delete(reporte);
    }
}