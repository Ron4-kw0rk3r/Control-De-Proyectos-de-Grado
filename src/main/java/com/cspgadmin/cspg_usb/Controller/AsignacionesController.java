package com.cspgadmin.cspg_usb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/asignaciones")
public class AsignacionesController {

    @GetMapping("/tutores")
    public String tutores(Model model) {
        return "admin/asignaciones/tutores";
    }

    @GetMapping("/tribunales")
    public String tribunales(Model model) {
        return "admin/asignaciones/tribunales";
    }

    @GetMapping("/defensas")
    public String defensas(Model model) {
        return "admin/asignaciones/defensas";
    }
} 