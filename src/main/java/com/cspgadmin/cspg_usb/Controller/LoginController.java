package com.cspgadmin.cspg_usb.Controller;

import com.cspgadmin.cspg_usb.Model.Usuario;
import com.cspgadmin.cspg_usb.Service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/index")
    public String showLoginPage() {
        return "index";
    }

    @PostMapping("/index")
    public String handleLogin(@RequestParam("email") String email, 
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        try {
            Optional<Usuario> usuario = usuarioService.validateUserByEmail(email);
            
            if (usuario.isPresent()) {
                Usuario user = usuario.get();
                session.setAttribute("usuario", user);
                session.setAttribute("rol", user.getRol());
                session.setMaxInactiveInterval(3600); // 1 hora
                
                return "redirect:" + determineRedirectUrl(user.getRol());
            }
            
            redirectAttributes.addFlashAttribute("error", "Correo electrónico no válido o usuario inactivo");
            return "redirect:/index";
                
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error interno del servidor");
            return "redirect:/index";
        }
    }

    private String determineRedirectUrl(String rol) {
        return switch (rol.toUpperCase()) {
            case "ROOT_ADMIN", "ADMIN" -> "/admrole";
            case "DOCENTE" -> "/docente/main";
            case "ESTUDIANTE" -> "/main";
            default -> "/index";
        };
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        redirectAttributes.addFlashAttribute("message", "Sesión cerrada correctamente");
        return "redirect:/index";
    }
}