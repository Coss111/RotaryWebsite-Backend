package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.model.Member;
import com.rotarywebsite.backend.model.MembershipStatus;
import com.rotarywebsite.backend.model.ProjectStatus;
import com.rotarywebsite.backend.model.Report;
import com.rotarywebsite.backend.model.ReportType;
import com.rotarywebsite.backend.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reporteRepository;
    private final MemberService miembroService;
    private final ProjectService projectService;

    public ReportService(ReportRepository reporteRepository,
                         MemberService miembroService,
                         ProjectService projectService) {
        this.reporteRepository = reporteRepository;
        this.miembroService = miembroService;
        this.projectService = projectService;
    }

    @Transactional
    public Report generateMembershipReport(Long generadoPorId) {
        Member generadoPor = miembroService.getById(generadoPorId)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        long totalActivos = miembroService.countByStatus(MembershipStatus.ACTIVE);
        long totalPendientes = miembroService.countByStatus(MembershipStatus.PENDING_RENEWAL);
        long totalInactivos = miembroService.countByStatus(MembershipStatus.INACTIVE);
        long totalSuspendidos = miembroService.countByStatus(MembershipStatus.SUSPENDED);

        String contenido = String.format(
                "{\"active\": %d, \"pendingRenewal\": %d, \"inactive\": %d, \"suspended\": %d, \"generatedAt\": \"%s\"}",
                totalActivos,
                totalPendientes,
                totalInactivos,
                totalSuspendidos,
                LocalDateTime.now()
        );

        Report reporte = new Report("Reporte de Membresías", ReportType.MEMBERSHIPS, generadoPor);
        reporte.setContenido(contenido);

        return reporteRepository.save(reporte);
    }

    @Transactional
    public Report generateProjectReport(Long generadoPorId) {
        Member generadoPor = miembroService.getById(generadoPorId)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        long planning = projectService.countByStatus(ProjectStatus.PLANNING);
        long inProgress = projectService.countByStatus(ProjectStatus.IN_PROGRESS);
        long completed = projectService.countByStatus(ProjectStatus.COMPLETED);
        long cancelled = projectService.countByStatus(ProjectStatus.CANCELLED);
        long suspended = projectService.countByStatus(ProjectStatus.SUSPENDED);

        String contenido = String.format(
                "{\"planning\": %d, \"inProgress\": %d, \"completed\": %d, \"cancelled\": %d, \"suspended\": %d, \"generatedAt\": \"%s\"}",
                planning,
                inProgress,
                completed,
                cancelled,
                suspended,
                LocalDateTime.now()
        );

        Report reporte = new Report("Reporte de Proyectos", ReportType.PROJECTS, generadoPor);
        reporte.setContenido(contenido);

        return reporteRepository.save(reporte);
    }

    @Transactional(readOnly = true)
    public List<Report> getAll() {
        return reporteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Report getById(Long id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Report> getByType(ReportType tipo) {
        return reporteRepository.findByTipo(tipo);
    }

    @Transactional(readOnly = true)
    public List<Report> getLatestReports() {
        return reporteRepository.findTop5ByOrderByFechaGeneracionDesc();
    }

    @Transactional
    public void deleteReport(Long reporteId) {
        Report reporte = getById(reporteId);
        reporteRepository.delete(reporte);
    }
}