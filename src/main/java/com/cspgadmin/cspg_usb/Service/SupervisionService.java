package com.cspgadmin.cspg_usb.Service;

import com.cspgadmin.cspg_usb.Model.Supervision;
import com.cspgadmin.cspg_usb.Repository.SupervisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SupervisionService {

    @Autowired
    private SupervisionRepository supervisionRepository;

    public List<Map<String, Object>> getProximasReuniones(Long docenteId) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime finSemana = ahora.plusDays(7);
        
        List<Supervision> supervisiones = supervisionRepository.findByDocenteIdAndFechaBetween(
            docenteId, ahora, finSemana);
        
        List<Map<String, Object>> reuniones = new ArrayList<>();
        for (Supervision supervision : supervisiones) {
            Map<String, Object> reunion = new HashMap<>();
            reunion.put("id", supervision.getSupervision_id());
            reunion.put("estudiante", supervision.getProyecto().getEstudiante());
            reunion.put("fecha", supervision.getSupervisionDate());
            reunion.put("proyecto", supervision.getProyecto().getTitulo());
            reunion.put("estado", "PENDIENTE");
            reuniones.add(reunion);
        }
        
        return reuniones;
    }

    public int getReunionesHoy(Long docenteId) {
        LocalDateTime inicioHoy = LocalDateTime.now().withHour(0).withMinute(0);
        LocalDateTime finHoy = LocalDateTime.now().withHour(23).withMinute(59);
        
        return supervisionRepository.countByDocenteIdAndFechaBetween(
            docenteId, inicioHoy, finHoy);
    }

    public void responderReunion(Long supervisionId, boolean aceptar) {
        Supervision supervision = supervisionRepository.findById(supervisionId)
            .orElseThrow(() -> new RuntimeException("Reunión no encontrada"));
            
        supervision.setEstado(aceptar ? "ACEPTADA" : "RECHAZADA");
        supervisionRepository.save(supervision);
    }
} 