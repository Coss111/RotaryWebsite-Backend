package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.Member;
import com.rotarywebsite.backend.model.MembershipStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Override
    @EntityGraph(attributePaths = {"usuario"})
    List<Member> findAll();

    @Override
    @EntityGraph(attributePaths = {"usuario"})
    Optional<Member> findById(Long id);

    @EntityGraph(attributePaths = {"usuario"})
    List<Member> findByNombreContainingIgnoreCase(String nombre);

    @EntityGraph(attributePaths = {"usuario"})
    List<Member> findByEstadoMembresia(MembershipStatus estado);

    @EntityGraph(attributePaths = {"usuario"})
    Optional<Member> findByUsuarioEmail(String email);

    @EntityGraph(attributePaths = {"usuario"})
    Optional<Member> findByUsuarioId(Long usuarioId);

    @EntityGraph(attributePaths = {"usuario"})
    List<Member> findByEstadoMembresiaAndFechaRenovacionBetween(
            MembershipStatus estado,
            LocalDate desde,
            LocalDate hasta
    );

    long countByEstadoMembresia(MembershipStatus estado);

    boolean existsByUsuarioEmail(String email);
}