package com.cspgadmin.cspg_usb.Service;

import com.cspgadmin.cspg_usb.Model.Proyecto;
import com.cspgadmin.cspg_usb.Repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    public Map<String, Object> getDocenteStats(Long docenteId) {
        Map<String, Object> stats = new HashMap<>();
        
        // Proyectos activos
        List<Proyecto> proyectosActivos = proyectoRepository.findByDocenteIdAndEstado(docenteId, "ACTIVO");
        stats.put("totalProyectosActivos", proyectosActivos.size());
        
        // Proyectos por estado
        Map<String, Long> proyectosPorEstado = proyectoRepository.countProyectosByEstadoAndDocenteId(docenteId);
        stats.put("proyectosPorEstado", proyectosPorEstado);
        
        // Progreso promedio
        Double progresoPromedio = proyectoRepository.getProgresoPromedioByDocenteId(docenteId);
        stats.put("progresoPromedio", progresoPromedio != null ? progresoPromedio : 0.0);
        
        return stats;
    }

    public List<Proyecto> getProyectosActivosByDocente(Long docenteId) {
        return proyectoRepository.findByDocenteIdAndEstado(docenteId, "ACTIVO");
    }
} 
