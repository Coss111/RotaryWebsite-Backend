package com.rotarywebsite.backend.dto;

import com.rotarywebsite.backend.model.CanalNotificacion;
import com.rotarywebsite.backend.model.NotificationType;

import java.time.LocalDateTime;

public class NotificationDTO {

    private Long id;
    private String title;
    private String message;
    private NotificationType type;
    private CanalNotificacion channel;
    private Boolean read;
    private Boolean sent;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
    private String sendError;
    private Long recipientMemberId;
    private String recipientName;
    private String recipientEmail;

    public NotificationDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public NotificationType getType() {
        return type;
    }

    public CanalNotificacion getChannel() {
        return channel;
    }

    public Boolean getRead() {
        return read;
    }

    public Boolean getSent() {
        return sent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public String getSendError() {
        return sendError;
    }

    public Long getRecipientMemberId() {
        return recipientMemberId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public void setChannel(CanalNotificacion channel) {
        this.channel = channel;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public void setSendError(String sendError) {
        this.sendError = sendError;
    }

    public void setRecipientMemberId(Long recipientMemberId) {
        this.recipientMemberId = recipientMemberId;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }
}