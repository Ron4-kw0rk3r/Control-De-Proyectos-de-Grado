package com.cspgadmin.cspg_usb.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.cspgadmin.cspg_usb.Service.UsuarioService;
import com.cspgadmin.cspg_usb.Model.Usuario;

@Controller
@RequestMapping("/admin/gestion")
public class GestionUsuariosController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/docentes")
    public String gestionDocentes(Model model) {
        model.addAttribute("docentes", usuarioService.findByRol("DOCENTE"));
        return "admin/usuarios/docentes";
    }

    @GetMapping("/estudiantes")
    public String gestionEstudiantes(Model model) {
        model.addAttribute("estudiantes", usuarioService.findByRol("ESTUDIANTE"));
        return "admin/usuarios/estudiantes";
    }

    @GetMapping("/administradores")
    public String gestionAdministradores(Model model) {
        model.addAttribute("administradores", usuarioService.findByRol("ADMIN"));
        return "admin/usuarios/administradores";
    }

    @PostMapping("/agregar")
    public String agregarUsuario(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.save(usuario);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario agregado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al agregar usuario: " + e.getMessage());
        }
        return "redirect:/admin/gestion/" + usuario.getRol().toLowerCase() + "s";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarUsuario(@PathVariable Long id, @ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            usuario.setId(id);
            usuarioService.save(usuario);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario actualizado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar usuario: " + e.getMessage());
        }
        return "redirect:/admin/gestion/" + usuario.getRol().toLowerCase() + "s";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioService.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            String rol = usuario.getRol();
            usuarioService.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado exitosamente");
            return "redirect:/admin/gestion/" + rol.toLowerCase() + "s";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar usuario: " + e.getMessage());
            return "redirect:/admin/gestion";
        }
    }
} 