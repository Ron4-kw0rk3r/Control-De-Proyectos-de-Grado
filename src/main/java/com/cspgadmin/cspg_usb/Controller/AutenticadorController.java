package com.cspgadmin.cspg_usb.Controller;
import com.cspgadmin.cspg_usb.Service.Autenticacionsrv;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import reactor.core.publisher.Mono;




@Controller
public class AutenticadorController {
        
    private final Autenticacionsrv authenticationService;

    public AutenticadorController(Autenticacionsrv authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate")
    public Mono<String> authenticateUser(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        return authenticationService.authenticate()
                .map(status -> {
                    if (status == HttpStatus.FOUND) { // 302 status code
                        redirectAttributes.addFlashAttribute("email", email);
                        return "redirect:/main";
                    } else {
                        redirectAttributes.addFlashAttribute("error", "Autenticación fallida. Inténtalo de nuevo.");
                        return "redirect:/login";
                    }
                });
    }
}
