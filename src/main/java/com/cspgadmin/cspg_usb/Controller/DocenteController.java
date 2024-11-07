package com.cspgadmin.cspg_usb.Controller;

import com.cspgadmin.cspg_usb.Model.*;
import com.cspgadmin.cspg_usb.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

@Controller
@RequestMapping("/docente")
public class DocenteController {

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private SupervisionService supervisionService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Usuario docente = (Usuario) session.getAttribute("usuario");
        
        // Estadísticas generales
        Map<String, Object> stats = new HashMap<>();
        List<Proyecto> proyectosActivos = proyectoService.getProyectosActivosByDocente(docente.getId());
        
        // Datos para las tarjetas de estadísticas
        stats.put("totalProyectosActivos", proyectosActivos.size());
        stats.put("revisionesPendientes", proyectoService.getRevisionesPendientes(docente.getId()));
        stats.put("reunionesHoy", supervisionService.getReunionesHoy(docente.getId()));
        stats.put("mensajesNoLeidos", 5); // Ejemplo estático, implementar servicio de mensajería
        
        // Datos para el progreso y tendencias
        stats.put("progresoMensual", "+12%");
        stats.put("nuevasRevisionesSemana", 5);
        stats.put("proximaReunion", "2h");
        stats.put("mensajesUrgentes", 2);
        
        model.addAttribute("stats", stats);
        
        // Proyectos activos con detalles
        List<Map<String, Object>> proyectosDetallados = new ArrayList<>();
        for (Proyecto proyecto : proyectosActivos) {
            Map<String, Object> detalles = new HashMap<>();
            detalles.put("id", proyecto.getProyectoId());
            detalles.put("titulo", proyecto.getTitulo());
            detalles.put("estudiante", proyecto.getEstudiante());
            detalles.put("progreso", calcularProgreso(proyecto)); // Método para calcular el progreso
            detalles.put("ultimaActividad", proyecto.getCreadoEn());
            detalles.put("estado", proyecto.getEstado());
            proyectosDetallados.add(detalles);
        }
        model.addAttribute("proyectosActivos", proyectosDetallados);
        
        // Próximas reuniones
        List<Map<String, Object>> reuniones = supervisionService.getProximasReuniones(docente.getId());
        model.addAttribute("reunionesPendientes", reuniones);
        
        // Telemetría y actividad
        model.addAttribute("telemetria", activityLogService.getTelemetriaDocente(docente.getId(), LocalDate.now()));
        model.addAttribute("actividadSemanal", activityLogService.getActividadSemanal(docente.getId()));
        
        return "docente/dashboard";
    }

    private int calcularProgreso(Proyecto proyecto) {
        // Implementar lógica para calcular el progreso basado en etapas completadas
        // Por ejemplo:
        return (int) (Math.random() * 100); // Temporal, reemplazar con lógica real
    }

    @GetMapping("/telemetria")
    @ResponseBody
    public Map<String, Object> getTelemetria(HttpSession session) {
        Usuario docente = (Usuario) session.getAttribute("usuario");
        return activityLogService.getTelemetriaDocente(docente.getId(), LocalDate.now());
    }

    @PostMapping("/registrar-actividad")
    @ResponseBody
    public void registrarActividad(@RequestParam String tipo, HttpSession session) {
        Usuario docente = (Usuario) session.getAttribute("usuario");
        activityLogService.registrarActividad(docente.getId(), tipo, LocalDateTime.now());
    }

    @PostMapping("/reunion/{id}/responder")
    @ResponseBody
    public Map<String, String> responderReunion(@PathVariable Long id, @RequestParam boolean aceptar) {
        Map<String, String> response = new HashMap<>();
        try {
            supervisionService.responderReunion(id, aceptar);
            response.put("status", "success");
            response.put("message", aceptar ? "Reunión aceptada" : "Reunión rechazada");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al procesar la respuesta");
        }
        return response;
    }
} 