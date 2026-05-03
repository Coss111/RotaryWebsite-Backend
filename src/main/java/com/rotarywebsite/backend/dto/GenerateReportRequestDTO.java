package com.rotarywebsite.backend.dto;

public class GenerateReportRequestDTO {

    private Long generatedById;

    public GenerateReportRequestDTO() {
    }

    public Long getGeneratedById() {
        return generatedById;
    }

    public void setGeneratedById(Long generatedById) {
        this.generatedById = generatedById;
    }
}