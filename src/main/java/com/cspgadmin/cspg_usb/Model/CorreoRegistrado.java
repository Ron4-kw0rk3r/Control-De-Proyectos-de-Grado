package com.cspgadmin.cspg_usb.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "CorreosRegistrados")
public class CorreoRegistrado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long correoId;

    private String correo;

    private LocalDateTime fecha;

    // Getters y Setters
    public Long getCorreoId() {
        return correoId;
    }

    public void setCorreoId(Long correoId) {
        this.correoId = correoId;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
