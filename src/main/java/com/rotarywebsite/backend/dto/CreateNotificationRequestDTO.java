package com.rotarywebsite.backend.dto;

import com.rotarywebsite.backend.model.CanalNotificacion;
import com.rotarywebsite.backend.model.NotificationType;

public class CreateNotificationRequestDTO {

    private String title;
    private String message;
    private NotificationType type;
    private CanalNotificacion channel;
    private Long recipientMemberId;

    public CreateNotificationRequestDTO() {
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

    public Long getRecipientMemberId() {
        return recipientMemberId;
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

    public void setRecipientMemberId(Long recipientMemberId) {
        this.recipientMemberId = recipientMemberId;
    }
}