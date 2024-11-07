package com.cspgadmin.cspg_usb.Controller;

import com.cspgadmin.cspg_usb.Model.Usuario;
import com.cspgadmin.cspg_usb.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    // Dashboard principal
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "pages/admsession";
    }
    
    


    // Endpoints para cargar contenido dinámicamente
    @GetMapping("/content/dashboard")
    @ResponseBody
    public Map<String, Object> getDashboardData() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalUsuarios", usuarioService.findAll().size());
        data.put("totalEstudiantes", usuarioService.findByRol("ESTUDIANTE").size());
        data.put("totalDocentes", usuarioService.findByRol("DOCENTE").size());
        data.put("totalAdmins", usuarioService.findByRol("ADMIN").size());
        return data;
    }
    
    // Perfil de usuario
    @GetMapping("/perfil")
    public String perfilUsuario(Model model) {
        return "admin/perfil";
    }
} 