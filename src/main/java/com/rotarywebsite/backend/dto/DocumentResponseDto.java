package com.rotarywebsite.backend.dto;

import java.time.LocalDateTime;

public class DocumentResponseDto {

    private Long id;
    private String nombreArchivo;
    private String contentType;
    private Long sizeBytes;
    private LocalDateTime fechaSubida;
    private String downloadUrl;

    public DocumentResponseDto() {
    }

    public DocumentResponseDto(Long id, String nombreArchivo, String contentType,
                               Long sizeBytes, LocalDateTime fechaSubida, String downloadUrl) {
        this.id = id;
        this.nombreArchivo = nombreArchivo;
        this.contentType = contentType;
        this.sizeBytes = sizeBytes;
        this.fechaSubida = fechaSubida;
        this.downloadUrl = downloadUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(Long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public LocalDateTime getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDateTime fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}