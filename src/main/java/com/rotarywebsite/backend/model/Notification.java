package com.rotarywebsite.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private NotificationType tipo = NotificationType.GENERAL;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CanalNotificacion canal = CanalNotificacion.INTERNAL;

    @Column(nullable = false)
    private Boolean leida = false;

    @Column(nullable = false)
    private Boolean enviada = false;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(name = "error_envio", length = 500)
    private String errorEnvio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "destinatario_id", nullable = false)
    @JsonIgnore
    private Member destinatario;

    public Notification() {
    }

    public Notification(String titulo, String mensaje, NotificationType tipo,
                        CanalNotificacion canal, Member destinatario) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.canal = canal;
        this.destinatario = destinatario;
        this.fechaCreacion = LocalDateTime.now();
        this.leida = false;
        this.enviada = false;
    }

    @PrePersist
    protected void onCreate() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
        if (this.leida == null) {
            this.leida = false;
        }
        if (this.enviada == null) {
            this.enviada = false;
        }
        if (this.tipo == null) {
            this.tipo = NotificationType.GENERAL;
        }
        if (this.canal == null) {
            this.canal = CanalNotificacion.INTERNAL;
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public NotificationType getTipo() {
        return tipo;
    }

    public CanalNotificacion getCanal() {
        return canal;
    }

    public Boolean getLeida() {
        return leida;
    }

    public Boolean getEnviada() {
        return enviada;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public String getErrorEnvio() {
        return errorEnvio;
    }

    public Member getDestinatario() {
        return destinatario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setTipo(NotificationType tipo) {
        this.tipo = tipo;
    }

    public void setCanal(CanalNotificacion canal) {
        this.canal = canal;
    }

    public void setLeida(Boolean leida) {
        this.leida = leida;
    }

    public void setEnviada(Boolean enviada) {
        this.enviada = enviada;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public void setErrorEnvio(String errorEnvio) {
        this.errorEnvio = errorEnvio;
    }

    public void setDestinatario(Member destinatario) {
        this.destinatario = destinatario;
    }
}