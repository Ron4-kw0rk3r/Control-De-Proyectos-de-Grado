package com.cspgadmin.cspg_usb.Controller;



import com.cspgadmin.cspg_usb.Model.adminroot;
import com.cspgadmin.cspg_usb.Repository.admrootRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cspgadmin.cspg_usb.conexiondb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;



@Controller
public class rootdbController {

    @Autowired
    private admrootRepository admrootRepository;

    @PostMapping("/admlogin")
    public String admlogin(@RequestParam("id") String id, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        // Procesa el ID y la contraseña
        // Aquí puedes añadir lógica de autenticación
        adminroot adminroot = admrootRepository.findById(id).orElse(null);
        if (adminroot == null || !adminroot.getPassword().equals(password)) {
            redirectAttributes.addFlashAttribute("message", "ID o contraseña incorrectos.");
            return "redirect:/login";
        }
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        String expectedId = String.valueOf(currentYear);
        //System.out.println("ID: " + id);

        // Check if the ID and password are valid
        if (id == null || id.isEmpty() || password == null || password.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "ID o contraseña no pueden estar vacíos.");
            return "redirect:/login";
        }
        if (!id.equals(expectedId)) {
            redirectAttributes.addFlashAttribute("message", "ID no es válido. Debe ser el año actual.");
            return "redirect:/login";
        }



        // Aquí puedes añadir lógica adicional para validar el ID y la contraseña
        // Por ejemplo, puedes verificar el ID y la contraseña en una base de datos


        // Aquí puedes añadir lógica adicional para validar el ID y la contraseña en la base de datos
        boolean isValidUser = false;
        Connection connection = conexiondb.connect();
        String query = "SELECT COUNT(*) FROM adminroot WHERE id = ? AND password = ?";
        try (connection; PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    isValidUser = true;
                    }
                } 
            

            } catch (SQLException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("message", "Error al conectar con la base de datos.");
                return "redirect:/login";
            }
        
        if (isValidUser) {
            // Si la autenticación es exitosa, redirige a la página de sesión de administrador
            return "redirect:/admsession";
        } else {
            // Si la autenticación falla, redirige a la página de inicio de sesión con un mensaje de error
            redirectAttributes.addFlashAttribute("message", "ID o contraseña incorrectos.");
            return "redirect:/login";
        }

    }
}

