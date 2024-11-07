package com.cspgadmin.cspg_usb.Controller;

import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
// The import statement for 'reques' seems to be incomplete. 
// Assuming it was meant to be 'import org.springframework.web.bind.annotation.RequestMapping;'
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;



@Controller
public class pointsController {
    
    @GetMapping(value = "/")
    public String home() {
        return "cspgUSB";
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }



    @RequestMapping(value = "/usbquery")
    public String usbquery() {
        return "usbquery";
    }

    @RequestMapping(value = "/main")
    public String main() {
        return "main";
    }

    @RequestMapping(value = "/pages/sistemas1")
    public String sistemas1() {
        return "pages/sistemas1";
    }
    
    @RequestMapping(value = "/pages/contaduria")
    public String contaduria() {
        return "pages/contaduria";
    }

    @RequestMapping(value = "/pages/comercial")
    public String comercial() {
        return "pages/comercial";
    }

    @RequestMapping(value = "/pages/derecho")
    public String derecho() {
        return "pages/derecho";
    }

    @RequestMapping(value = "/pages/parvularia")
    public String parvularia() {
        return "pages/parvularia";
    }

    @RequestMapping(value = "/pages/gastronomia")
    public String gastronomia() {
        return "pages/gastronomia";
    }

    @RequestMapping(value = "/pages/educacion")
    public String educacion() {
        return "pages/educacion";
    }
    /*

    @RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the session to clear any data
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Close any active database connections
        try {
            java.sql.DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
                try {
                    java.sql.DriverManager.deregisterDriver(driver);
                } catch (SQLException e) {
                    // Log any errors that occur during connection cleanup
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            // Log any errors that occur during connection cleanup
            e.printStackTrace();
        }
        // Clear the cache to prevent the browser from using the previous session's data
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        request.getSession(true);

        // Clear Spring Security context
        /*SecurityContextHolder.clearContext();
        
        // Clear authentication
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        // Redirect to the index page
        return "redirect:/";
    } 
    */


    @RequestMapping(value = "/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }






    @RequestMapping(value = "/admrole")
    public String admrole() {
        return "pages/adminrole";
    }

    @RequestMapping(value = "/admsession")
    public String admsession() {
        return "pages/admsession";
    }

    @RequestMapping(value = "/asignestudent")
    public String asignestudent() {
        return "pages/asignestudent";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "index";
    }

    /* 
    @GetMapping("/main")
    public String getMainPage(Model model, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        String userRole = (String) session.getAttribute("userRole");
        
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("userRole", userRole);
        
        return "main";
    }
    */
    /// configuracion del request

    
    @RequestMapping(value = "/docente/board")
    public String docenteboard() {
        return "pages/docenteboard";
    }

    @RequestMapping(value = "/estudiante/board")
    public String estudianteboard() {
        return "pages/estudianteboard";
    }

}
