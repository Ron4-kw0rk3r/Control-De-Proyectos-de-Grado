package com.cspgadmin.cspg_usb.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "etapas")
public class Etapa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long etapa_id;
    
    @Column(name = "nombre_etapa", nullable = false)
    private String nombreEtapa;
    
    private String descripcion;
    
    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;
    
    private boolean completado;
    
    @Column(name = "creado_en")
    private LocalDateTime creadoEn;
    
    @PrePersist
    protected void onCreate() {
        creadoEn = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getEtapa_id() {
        return etapa_id;
    }

    public void setEtapa_id(Long etapa_id) {
        this.etapa_id = etapa_id;
    }

    public String getNombreEtapa() {
        return nombreEtapa;
    }

    public void setNombreEtapa(String nombreEtapa) {
        this.nombreEtapa = nombreEtapa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }
} 