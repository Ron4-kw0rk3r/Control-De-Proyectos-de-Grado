package com.cspgadmin.cspg_usb.Model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "proyectos")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proyecto_id")
    private Long proyectoId;
    
    @Column(nullable = false)
    private String titulo;
    
    @Column(nullable = false)
    private String descripcion;
    
    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Usuario estudiante;
    
    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Usuario docente;
    
    @Column(nullable = false)
    private String estado;
    
    @Column(name = "creado_en")
    private LocalDateTime creadoEn;
    
    @PrePersist
    protected void onCreate() {
        creadoEn = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Long proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Usuario estudiante) {
        this.estudiante = estudiante;
    }

    public Usuario getDocente() {
        return docente;
    }

    public void setDocente(Usuario docente) {
        this.docente = docente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "proyectoId=" + proyectoId +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estudiante=" + estudiante +
                ", docente=" + docente +
                ", estado='" + estado + '\'' +
                ", creadoEn=" + creadoEn +
                '}';
    }
} 