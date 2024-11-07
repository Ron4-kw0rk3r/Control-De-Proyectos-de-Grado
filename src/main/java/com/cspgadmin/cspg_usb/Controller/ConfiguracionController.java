package com.cspgadmin.cspg_usb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/config")
public class ConfiguracionController {
    
    @GetMapping("/general")
    public String configuracionGeneral(Model model) {
        return "admin/configuracion/general";
    }
    
    @GetMapping("/permisos")
    public String gestionPermisos(Model model) {
        return "admin/configuracion/permisos";
    }
    
    @GetMapping("/logs")
    public String logsDelSistema(Model model) {
        return "admin/configuracion/logs";
    }
} 