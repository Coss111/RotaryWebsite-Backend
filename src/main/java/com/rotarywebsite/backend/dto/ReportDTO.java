package com.rotarywebsite.backend.dto;

import com.rotarywebsite.backend.model.ReportType;

import java.time.LocalDateTime;

public class ReportDTO {

    private Long id;
    private String name;
    private ReportType type;
    private String content;
    private String fileUrl;
    private LocalDateTime generatedAt;
    private Long generatedById;
    private String generatedByName;

    public ReportDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ReportType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public Long getGeneratedById() {
        return generatedById;
    }

    public String getGeneratedByName() {
        return generatedByName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public void setGeneratedById(Long generatedById) {
        this.generatedById = generatedById;
    }

    public void setGeneratedByName(String generatedByName) {
        this.generatedByName = generatedByName;
    }
}