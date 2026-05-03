package com.rotarywebsite.backend.dto;

public class UserStatusUpdateRequest {

    private Boolean active;

    public UserStatusUpdateRequest() {
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}