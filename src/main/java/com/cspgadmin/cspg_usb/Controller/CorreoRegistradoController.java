package com.cspgadmin.cspg_usb.Controller;

import com.cspgadmin.cspg_usb.Model.CorreoRegistrado;
import com.cspgadmin.cspg_usb.Repository.CorreoRegistradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
// import java.util.List;
import com.cspgadmin.cspg_usb.conexiondb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.sql.Timestamp;

@Controller

public class CorreoRegistradoController {

    @Autowired
    private CorreoRegistradoRepository correoRegistradoRepository;

    @PostMapping("/registrar")
    

    public String registrarCorreo(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        CorreoRegistrado nuevoCorreo = new CorreoRegistrado();
        nuevoCorreo.setCorreo(email);

        // ######################### CONEXION A LA BASE DE DATOS REGISTRO DE CORREO #########################

        Connection connection = conexiondb.connect();
        String query = "INSERT INTO CorreosRegistrados (correo) VALUES (?)";

        try (connection; PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            //preparedStatement.setTimestamp(2, Timestamp.valueOf(nuevoCorreo.getFecha()));
            preparedStatement.executeUpdate();


            correoRegistradoRepository.save(nuevoCorreo);
            redirectAttributes.addFlashAttribute("email", email);



            String username = email.split("@")[0];

            // Convert the username to a vector of words
            String[] words = username.split("\\.");

            // Check if any of the words is "root"
            for (String word : words) {
                if (word.equalsIgnoreCase("root")) {
                    // Redirect to admin role page if "root" is found
                    return "redirect:/adminrole";
                }
            }
            return "redirect:/main"; // Redirige a main.html

        } catch (SQLException e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar el correo en la base de datos: " + e.getMessage());
            return "redirect:/usbquery"; // Redirige a una página de error o reintento
        }

        


        // ###########################################################################################
        // Parse the email to extract the username part
        
        
    }

    


    // Aquí puedes añadir otros métodos para manejar operaciones como PUT, DELETE, etc.
    // Por ejemplo, puedes añadir un método PUT para actualizar un correo registrado existente en la base de datos.
    // También puedes añadir un método DELETE para eliminar un correo registrado de la base de datos.
    // Estos métodos interactuarán con el repositorio de JPA para realizar las operaciones correspondientes en la base de datos.
}