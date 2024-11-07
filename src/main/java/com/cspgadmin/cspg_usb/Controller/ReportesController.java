package com.cspgadmin.cspg_usb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/reportes")
public class ReportesController {
    
    @GetMapping("/lista/estadisticas")
    public String estadisticas(Model model) {
        return "admin/reportes/estadisticas";
    }
    
    @GetMapping("/lista/informes")
    public String informes(Model model) {
        return "admin/reportes/informes";
    }
    
    @GetMapping("/lista/exportar")
    public String exportarDatos(Model model) {
        return "admin/reportes/exportar";
    }
} 