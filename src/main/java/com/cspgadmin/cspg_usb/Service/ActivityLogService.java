package com.cspgadmin.cspg_usb.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class ActivityLogService {
    
    @Autowired
    private HttpServletRequest request;

    public void logActivity(Long usuarioId, String tipoActividad, String descripcion) {
        // Implementar el registro de actividad en la base de datos
        String ipAddress = getClientIp();
        String userAgent = request.getHeader("User-Agent");
        
        // Aquí deberías guardar en la tabla de logs
    }

    private String getClientIp() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
} 