package com.cspgadmin.cspg_usb.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "supervision")
public class Supervision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supervision_id")
    private Long supervisionId;
    
    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;
    
    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Usuario docente;
    
    @Column(name = "supervision_date")
    private LocalDateTime supervisionDate;
    
    @PrePersist
    protected void onCreate() {
        supervisionDate = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getSupervision_id() {
        return supervisionId;
    }

    public void setSupervision_id(Long supervisionId) {
        this.supervisionId = supervisionId;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Usuario getDocente() {
        return docente;
    }

    public void setDocente(Usuario docente) {
        this.docente = docente;
    }

    public LocalDateTime getSupervisionDate() {
        return supervisionDate;
    }

    public void setSupervisionDate(LocalDateTime supervisionDate) {
        this.supervisionDate = supervisionDate;
    }
} 