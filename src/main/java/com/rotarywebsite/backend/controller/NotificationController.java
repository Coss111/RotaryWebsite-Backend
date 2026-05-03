package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.dto.CreateNotificationRequestDTO;
import com.rotarywebsite.backend.dto.NotificationDTO;
import com.rotarywebsite.backend.model.Notification;
import com.rotarywebsite.backend.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAll() {
        List<NotificationDTO> notifications = notificationService.getAll()
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(toDto(notificationService.getById(id)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/miembro/{memberId}")
    public ResponseEntity<List<NotificationDTO>> getByMember(@PathVariable Long memberId) {
        List<NotificationDTO> notifications = notificationService.getByMember(memberId)
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/miembro/{memberId}/no-leidas")
    public ResponseEntity<List<NotificationDTO>> getUnreadByMember(@PathVariable Long memberId) {
        List<NotificationDTO> notifications = notificationService.getUnreadByMember(memberId)
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/miembro/{memberId}/conteo-no-leidas")
    public ResponseEntity<Map<String, Long>> countUnreadByMember(@PathVariable Long memberId) {
        long total = notificationService.countUnreadByMember(memberId);
        return ResponseEntity.ok(Map.of("unreadCount", total));
    }

    @PostMapping
    public ResponseEntity<?> createNotification(@RequestBody CreateNotificationRequestDTO dto) {
        try {
            Notification notification = notificationService.createNotification(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDto(notification));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/recordatorios-renovacion")
    public ResponseEntity<Map<String, Integer>> sendRenewalReminders() {
        int total = notificationService.sendMembershipRenewalReminders();
        return ResponseEntity.ok(Map.of("notificationsGenerated", total));
    }

    @PutMapping("/{id}/leer")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        try {
            Notification notification = notificationService.markAsRead(id);
            return ResponseEntity.ok(toDto(notification));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        try {
            notificationService.deleteNotification(id);
            return ResponseEntity.ok(Map.of("message", "Notificación eliminada exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    private NotificationDTO toDto(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setTitle(notification.getTitulo());
        dto.setMessage(notification.getMensaje());
        dto.setType(notification.getTipo());
        dto.setChannel(notification.getCanal());
        dto.setRead(notification.getLeida());
        dto.setSent(notification.getEnviada());
        dto.setCreatedAt(notification.getFechaCreacion());
        dto.setSentAt(notification.getFechaEnvio());
        dto.setSendError(notification.getErrorEnvio());
        if (notification.getDestinatario() != null) {
            dto.setRecipientMemberId(notification.getDestinatario().getId());
            dto.setRecipientName(notification.getDestinatario().getNombre());

            if (notification.getDestinatario().getUsuario() != null) {
                dto.setRecipientEmail(notification.getDestinatario().getUsuario().getEmail());
            }
        }
        return dto;
    }

}