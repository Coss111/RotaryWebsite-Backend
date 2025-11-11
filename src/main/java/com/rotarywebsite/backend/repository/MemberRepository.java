package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.Member;
import com.rotarywebsite.backend.model.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    // Buscar miembro por nombre
    List<Member> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar miembros por estado de membresía
    List<Member> findByEstadoMembresia(MembershipStatus estado);
    
    // Buscar miembro por email del usuario
    Optional<Member> findByUsuarioEmail(String email);
    
    // Buscar miembros que necesitan renovación (próximos 30 días)
    List<Member> findByFechaRenovacionLessThan(java.time.LocalDate fechaLimite);
    
    // Contar miembros por estado
    long countByEstadoMembresia(MembershipStatus estado);
    
    // Buscar miembros activos (estado ACTIVO)
    List<Member> findByEstadoMembresiaAndUsuarioActivoTrue(MembershipStatus estado);
}