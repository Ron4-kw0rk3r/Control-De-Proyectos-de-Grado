package com.cspgadmin.cspg_usb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/proyectos")
public class ProyectosController {
    
    @GetMapping("/lista/activos")
    public String proyectosActivos(Model model) {
        return "admin/proyectos/activos";
    }
    
    @GetMapping("/lista/seguimiento")
    public String seguimientoProyectos(Model model) {
        return "admin/proyectos/seguimiento";
    }
    
    @GetMapping("/lista/aprobaciones")
    public String aprobacionesProyectos(Model model) {
        return "admin/proyectos/aprobaciones";
    }
} 