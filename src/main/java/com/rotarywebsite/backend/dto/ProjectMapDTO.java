package com.rotarywebsite.backend.dto;

import com.rotarywebsite.backend.model.ProjectStatus;

public class ProjectMapDTO {

    private Long id;
    private String name;
    private ProjectStatus status;
    private String location;
    private String socialImpact;
    private Double latitude;
    private Double longitude;
    private Long creatorId;
    private String creatorName;

    public ProjectMapDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public String getSocialImpact() {
        return socialImpact;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSocialImpact(String socialImpact) {
        this.socialImpact = socialImpact;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}