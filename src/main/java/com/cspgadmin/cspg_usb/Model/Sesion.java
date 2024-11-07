package com.cspgadmin.cspg_usb.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usersession")
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String sessionId;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "ultimo_acceso")
    private LocalDateTime ultimoAcceso;
    
    @Column(nullable = false)
    private boolean activa;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        ultimoAcceso = LocalDateTime.now();
        activa = true;
    }
    
    @PreUpdate
    protected void onUpdate() {
        ultimoAcceso = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    @Override
    public String toString() {
        return "Sesion{" +
                "id=" + id +
                ", sessionId='" + sessionId + '\'' +
                ", usuario=" + usuario +
                ", fechaCreacion=" + fechaCreacion +
                ", ultimoAcceso=" + ultimoAcceso +
                ", activa=" + activa +
                '}';
    }
} 