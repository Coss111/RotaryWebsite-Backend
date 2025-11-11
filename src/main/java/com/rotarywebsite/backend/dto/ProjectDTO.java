package com.rotarywebsite.backend.dto;

import com.rotarywebsite.backend.model.ProjectStatus;
import java.time.LocalDate;

public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private ProjectStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String socialImpact;
    private Double budget;
    private String location;
    private Integer beneficiaryCount;
    private String responsiblePerson;
    private Boolean newGenerationsParticipation;
    private Boolean otherClubsParticipation;
    private String participatingInstitution;
    private String myRotaryUrl;
    private Double latitude;
    private Double longitude;
    private Long creatorId;
    private String creatorName;

    // Constructors, Getters and Setters
    public ProjectDTO() {}
    
    // Getters and Setters for all fields
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public ProjectStatus getStatus() { return status; }
    public void setStatus(ProjectStatus status) { this.status = status; }
    
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    
    public String getSocialImpact() { return socialImpact; }
    public void setSocialImpact(String socialImpact) { this.socialImpact = socialImpact; }
    
    public Double getBudget() { return budget; }
    public void setBudget(Double budget) { this.budget = budget; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public Integer getBeneficiaryCount() { return beneficiaryCount; }
    public void setBeneficiaryCount(Integer beneficiaryCount) { this.beneficiaryCount = beneficiaryCount; }
    
    public String getResponsiblePerson() { return responsiblePerson; }
    public void setResponsiblePerson(String responsiblePerson) { this.responsiblePerson = responsiblePerson; }
    
    public Boolean getNewGenerationsParticipation() { return newGenerationsParticipation; }
    public void setNewGenerationsParticipation(Boolean newGenerationsParticipation) { this.newGenerationsParticipation = newGenerationsParticipation; }
    
    public Boolean getOtherClubsParticipation() { return otherClubsParticipation; }
    public void setOtherClubsParticipation(Boolean otherClubsParticipation) { this.otherClubsParticipation = otherClubsParticipation; }
    
    public String getParticipatingInstitution() { return participatingInstitution; }
    public void setParticipatingInstitution(String participatingInstitution) { this.participatingInstitution = participatingInstitution; }
    
    public String getMyRotaryUrl() { return myRotaryUrl; }
    public void setMyRotaryUrl(String myRotaryUrl) { this.myRotaryUrl = myRotaryUrl; }
    
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    
    public Long getCreatorId() { return creatorId; }
    public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }
    
    public String getCreatorName() { return creatorName; }
    public void setCreatorName(String creatorName) { this.creatorName = creatorName; }
}