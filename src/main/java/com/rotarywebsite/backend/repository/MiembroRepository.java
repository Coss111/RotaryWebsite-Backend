package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.Miembro;
import com.rotarywebsite.backend.model.EstadoMembresia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MiembroRepository extends JpaRepository<Miembro, Long> {
    
    // Buscar miembro por nombre
    List<Miembro> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar miembros por estado de membresía
    List<Miembro> findByEstadoMembresia(EstadoMembresia estado);
    
    // Buscar miembro por email del usuario
    Optional<Miembro> findByUsuarioEmail(String email);
    
    // Buscar miembros que necesitan renovación (próximos 30 días)
    List<Miembro> findByFechaRenovacionLessThan(java.time.LocalDate fechaLimite);
    
    // Contar miembros por estado
    long countByEstadoMembresia(EstadoMembresia estado);
    
    // Buscar miembros activos (estado ACTIVO)
    List<Miembro> findByEstadoMembresiaAndUsuarioActivoTrue(EstadoMembresia estado);
}