package com.cspgadmin.cspg_usb.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cspgadmin.cspg_usb.Model.Proyecto;
import com.cspgadmin.cspg_usb.Model.Etapa;
import com.cspgadmin.cspg_usb.Model.Supervision;
import com.cspgadmin.cspg_usb.Repository.ProyectoRepository;
import com.cspgadmin.cspg_usb.Repository.EtapaRepository;
import com.cspgadmin.cspg_usb.Repository.SupervisionRepository;
import com.cspgadmin.cspg_usb.Model.Usuario;
import com.cspgadmin.cspg_usb.Repository.UsuarioRepository;

@Service
@Transactional
public class ProyectoService {
    
    @Autowired
    private ProyectoRepository proyectoRepository;
    
    @Autowired
    private EtapaRepository etapaRepository;
    
    @Autowired
    private SupervisionRepository supervisionRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Proyecto crearProyecto(Proyecto proyecto) {
        // Validar datos
        if (proyecto.getTitulo() == null || proyecto.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título es requerido");
        }
        
        // Establecer estado inicial
        proyecto.setEstado("PENDIENTE");
        
        // Guardar proyecto
        Proyecto proyectoGuardado = proyectoRepository.save(proyecto);
        
        // Crear etapas iniciales
        crearEtapasIniciales(proyectoGuardado);
        
        return proyectoGuardado;
    }
    
    private void crearEtapasIniciales(Proyecto proyecto) {
        List<String> etapasIniciales = Arrays.asList(
            "Definición del Tema",
            "Elaboración del Perfil",
            "Desarrollo del Marco Teórico",
            "Metodología",
            "Desarrollo",
            "Conclusiones"
        );
        
        etapasIniciales.forEach(nombreEtapa -> {
            Etapa etapa = new Etapa();
            etapa.setNombreEtapa(nombreEtapa);
            etapa.setProyecto(proyecto);
            etapa.setCompletado(false);
            etapaRepository.save(etapa);
        });
    }
    
    public void registrarSupervision(Long proyectoId, Long docenteId) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
            .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
            
        Usuario docente = usuarioRepository.findById(docenteId)
            .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
            
        Supervision supervision = new Supervision();
        supervision.setProyecto(proyecto);
        supervision.setDocente(docente);
        
        supervisionRepository.save(supervision);
    }
    
    public double calcularProgreso(Long proyectoId) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
            .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
            
        long etapasCompletadas = etapaRepository.countCompletadasByProyecto(proyecto);
        long totalEtapas = etapaRepository.findByProyecto(proyecto).size();
        
        return (double) etapasCompletadas / totalEtapas * 100;
    }
    
    public void actualizarEstadoProyecto(Long proyectoId, String nuevoEstado) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
            .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
            
        proyecto.setEstado(nuevoEstado);
        proyectoRepository.save(proyecto);
    }
    
    // Métodos para reportes y estadísticas
    public Map<String, Long> obtenerEstadisticasPorEstado() {
        return Arrays.asList("PENDIENTE", "EN_PROCESO", "COMPLETADO", "SUSPENDIDO")
            .stream()
            .collect(Collectors.toMap(
                estado -> estado,
                estado -> proyectoRepository.countByEstado(estado)
            ));
    }
    
    public Map<String, List<Proyecto>> obtenerProyectosPorCarrera() {
        return proyectoRepository.findAll()
            .stream()
            .filter(proyecto -> proyecto.getEstudiante() != null && proyecto.getEstudiante().getCarrera() != null)
            .collect(Collectors.groupingBy(
                proyecto -> proyecto.getEstudiante().getCarrera()
            ));
    }
} 