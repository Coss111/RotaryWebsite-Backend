package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.dto.CreateNotificationRequestDTO;
import com.rotarywebsite.backend.model.CanalNotificacion;
import com.rotarywebsite.backend.model.Member;
import com.rotarywebsite.backend.model.Notification;
import com.rotarywebsite.backend.model.NotificationType;
import com.rotarywebsite.backend.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberService memberService;
    private final EmailService emailService;

    public NotificationService(NotificationRepository notificationRepository,
                               MemberService memberService,
                               EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.memberService = memberService;
        this.emailService = emailService;
    }

    @Transactional
    public Notification createNotification(CreateNotificationRequestDTO dto) {
        if (dto.getRecipientMemberId() == null) {
            throw new RuntimeException("recipientMemberId es obligatorio");
        }

        Member destinatario = memberService.getById(dto.getRecipientMemberId())
                .orElseThrow(() -> new RuntimeException("Miembro destinatario no encontrado"));

        Notification notification = new Notification(
                dto.getTitle(),
                dto.getMessage(),
                dto.getType() != null ? dto.getType() : NotificationType.GENERAL,
                dto.getChannel() != null ? dto.getChannel() : CanalNotificacion.INTERNAL,
                destinatario
        );

        if (notification.getCanal() == CanalNotificacion.INTERNAL) {
            notification.setEnviada(true);
            notification.setFechaEnvio(LocalDateTime.now());
            return notificationRepository.save(notification);
        }

        try {
            String email = destinatario.getUsuario() != null ? destinatario.getUsuario().getEmail() : null;

            if (email == null || email.isBlank()) {
                throw new RuntimeException("El destinatario no tiene email válido");
            }

            emailService.sendSimpleMessage(email, dto.getTitle(), dto.getMessage());
            notification.setEnviada(true);
            notification.setFechaEnvio(LocalDateTime.now());
        } catch (Exception e) {
            notification.setEnviada(false);
            notification.setErrorEnvio(e.getMessage());
        }

        return notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Notification getById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
    }

    @Transactional(readOnly = true)
    public List<Notification> getByMember(Long memberId) {
        return notificationRepository.findByDestinatarioIdOrderByFechaCreacionDesc(memberId);
    }

    @Transactional(readOnly = true)
    public List<Notification> getUnreadByMember(Long memberId) {
        return notificationRepository.findByDestinatarioIdAndLeidaFalseOrderByFechaCreacionDesc(memberId);
    }

    @Transactional(readOnly = true)
    public long countUnreadByMember(Long memberId) {
        return notificationRepository.countByDestinatarioIdAndLeidaFalse(memberId);
    }

    @Transactional
    public Notification markAsRead(Long notificationId) {
        Notification notification = getById(notificationId);
        notification.setLeida(true);
        return notificationRepository.save(notification);
    }

    @Transactional
    public void deleteNotification(Long notificationId) {
        Notification notification = getById(notificationId);
        notificationRepository.delete(notification);
    }

    @Transactional
    public int sendMembershipRenewalReminders() {
        List<Member> pendingMembers = memberService.getPendingRenewal();
        int total = 0;

        for (Member member : pendingMembers) {
            CreateNotificationRequestDTO dto = new CreateNotificationRequestDTO();
            dto.setRecipientMemberId(member.getId());
            dto.setTitle("Recordatorio de renovación de membresía");
            dto.setMessage("Tu membresía está próxima a vencer. Fecha de renovación: " + member.getFechaRenovacion());
            dto.setType(NotificationType.MEMBERSHIP_EXPIRATION);
            dto.setChannel(CanalNotificacion.BOTH);

            createNotification(dto);
            total++;
        }

        return total;
    }
}