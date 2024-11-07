package com.cspgadmin.cspg_usb.Service;

import com.cspgadmin.cspg_usb.Model.ActivityLog;
import com.cspgadmin.cspg_usb.Repository.ActivityLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class ActivityLogService {
    
    @Autowired
    private ActivityLogRepository activityLogRepository;

    public Map<String, Object> getTelemetriaDocente(Long docenteId, LocalDate fecha) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.atTime(LocalTime.MAX);
        
        List<ActivityLog> logs = activityLogRepository.findByUsuarioIdAndFechaRegistroBetween(
            docenteId, inicio, fin);
        
        double[] actividades = new double[24];
        
        for (ActivityLog log : logs) {
            int hora = log.getFechaRegistro().getHour();
            String tipo = log.getTipoActividad();
            
            switch (tipo) {
                case "actividad":
                    actividades[hora] = Math.max(actividades[hora], 0.5);
                    break;
                case "reunion":
                case "chat":
                    actividades[hora] = 1.0;
                    break;
                default:
                    if (actividades[hora] == 0) {
                        actividades[hora] = 0;
                    }
            }
        }
        
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("actividades", actividades);
        return resultado;
    }

    public List<Map<String, Object>> getActividadSemanal(Long docenteId) {
        List<Map<String, Object>> actividades = new ArrayList<>();
        LocalDateTime inicioSemana = LocalDateTime.now().minusDays(7);
        
        List<ActivityLog> logs = activityLogRepository.findByUsuarioIdAndFechaRegistroBetween(
            docenteId, inicioSemana, LocalDateTime.now());
        
        // Procesar logs y crear resumen de actividad semanal
        for (ActivityLog log : logs) {
            Map<String, Object> actividad = new HashMap<>();
            actividad.put("fecha", log.getFechaRegistro());
            actividad.put("tipo", log.getTipoActividad());
            actividad.put("descripcion", log.getDescripcion());
            actividades.add(actividad);
        }
        
        return actividades;
    }

    public void logActivity(Long usuarioId, String tipo, String descripcion, String ipAddress, String userAgent) {
        ActivityLog log = new ActivityLog();
        log.setUsuarioId(usuarioId);
        log.setTipoActividad(tipo);
        log.setDescripcion(descripcion);
        log.setIpAddress(ipAddress);
        log.setUserAgent(userAgent);
        activityLogRepository.save(log);
    }

    public void registrarActividad(Long usuarioId, String tipo, LocalDateTime fecha) {
        ActivityLog log = new ActivityLog();
        log.setUsuarioId(usuarioId);
        log.setTipoActividad(tipo);
        log.setFechaRegistro(fecha);
        activityLogRepository.save(log);
    }
} 