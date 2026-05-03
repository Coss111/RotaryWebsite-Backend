package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.Notification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Override
    @EntityGraph(attributePaths = {"destinatario", "destinatario.usuario"})
    List<Notification> findAll();

    @Override
    @EntityGraph(attributePaths = {"destinatario", "destinatario.usuario"})
    Optional<Notification> findById(Long id);

    @EntityGraph(attributePaths = {"destinatario", "destinatario.usuario"})
    List<Notification> findByDestinatarioIdOrderByFechaCreacionDesc(Long destinatarioId);

    @EntityGraph(attributePaths = {"destinatario", "destinatario.usuario"})
    List<Notification> findByDestinatarioIdAndLeidaFalseOrderByFechaCreacionDesc(Long destinatarioId);

    long countByDestinatarioIdAndLeidaFalse(Long destinatarioId);
}