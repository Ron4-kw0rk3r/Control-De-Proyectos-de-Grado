package com.cspgadmin.cspg_usb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        // Procesa el correo electrónico
        // Aquí puedes añadir lógica de autenticación
        // Parse the email to get the domain

        // Check if the email has a number before the '@' symbol

        String domain = email.substring(email.indexOf("@") + 1);

        // Check the user's role based on the domain
        String role;

        String localPart = email.substring(0, email.indexOf("@"));
        if (!localPart.matches(".*\\d.*")) {
            role = "administrador";
        }
        
        if (domain.equals("usalesiana.edu.bo")) {
            role = "estudiante";    
            
        } else if (domain.equals("servicios.usalesiana.edu.bo")) { // docente
            role = "servicios";
        } else {
            redirectAttributes.addFlashAttribute("message", "Acceso denegado: dominio no reconocido.");
            return "redirect:/login";
        }

        // Add the role to the redirect attributes
        redirectAttributes.addFlashAttribute("role", role);
        // Añade un mensaje de éxito o error
        redirectAttributes.addFlashAttribute("message", "Inicio de sesión exitoso para: " + email);

        // Redirige a la página de inicio o a otra página
        return "redirect:/home";
    }
}