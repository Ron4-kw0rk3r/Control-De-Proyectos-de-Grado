package com.cspgadmin.cspg_usb.Service;

import com.cspgadmin.cspg_usb.Model.Usuario;
import com.cspgadmin.cspg_usb.Repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailValidationService emailValidationService;

    @Autowired
    private ActivityLogService activityLogService;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> findByRol(String rol) {
        return usuarioRepository.findByRol(rol);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.validateUserEmail(email);
    }

    public Optional<Usuario> validateUserByEmail(String email) {
        log.debug("Validando usuario con email: {}", email);
        
        String rol = emailValidationService.determineUserRole(email);
        if (rol == null) {
            log.warn("Formato de email inválido: {}", email);
            return Optional.empty();
        }

        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(email);
        
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            if (!usuario.isActivo()) {
                log.warn("Usuario inactivo: {}", email);
                return Optional.empty();
            }
            
            usuario.setUltimoAcceso(LocalDateTime.now());
            usuarioRepository.save(usuario);
            
            activityLogService.logActivity(usuario.getId(), "LOGIN", "Inicio de sesión exitoso");
            
            return Optional.of(usuario);
        }
        
        if ("ROOT_ADMIN".equals(rol)) {
            log.info("Creando usuario ROOT_ADMIN por primera vez");
            return Optional.of(createRootAdmin(email));
        }
        
        if (verificarRegistroPrevio(email)) {
            return Optional.of(createNewUser(email, rol));
        }
        
        log.warn("Usuario no autorizado: {}", email);
        return Optional.empty();
    }

    private Usuario createRootAdmin(String email) {
        Usuario rootAdmin = new Usuario();
        rootAdmin.setEmail(email);
        rootAdmin.setNombre("Root");
        rootAdmin.setApellido("Administrator");
        rootAdmin.setRol("ROOT_ADMIN");
        rootAdmin.setActivo(true);
        rootAdmin.setFechaRegistro(LocalDateTime.now());
        rootAdmin.setUltimoAcceso(LocalDateTime.now());
        
        Usuario saved = usuarioRepository.save(rootAdmin);
        activityLogService.logActivity(saved.getId(), "CREAR_USUARIO", "Creación de usuario ROOT_ADMIN");
        return saved;
    }

    private Usuario createNewUser(String email, String rol) {
        String[] parts = email.split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Formato de email inválido");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNombre(capitalizeFirst(parts[0]));
        usuario.setApellido(capitalizeFirst(parts[1]));
        usuario.setRol(rol);
        usuario.setActivo(true);
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setUltimoAcceso(LocalDateTime.now());
        
        if ("ESTUDIANTE".equals(rol) || "DOCENTE".equals(rol)) {
            usuario.setCarrera(determinarCarrera(email));
        }
        
        Usuario saved = usuarioRepository.save(usuario);
        activityLogService.logActivity(saved.getId(), "CREAR_USUARIO", 
            String.format("Creación de usuario %s: %s", rol, email));
        return saved;
    }

    private boolean verificarRegistroPrevio(String email) {
        return true;
    }

    private String determinarCarrera(String email) {
        String[] parts = email.split("\\.");
        if (parts.length >= 3) {
            String codigo = parts[2].substring(0, 3);
            return mapCodigoACarrera(codigo);
        }
        return "No especificada";
    }

    private String mapCodigoACarrera(String codigo) {
        return switch (codigo) {
            case "611" -> "Ingeniería de Sistemas";
            case "612" -> "Ingeniería Comercial";
            case "613" -> "Derecho";
            case "614" -> "Psicología";
            default -> "No especificada";
        };
    }

    private String capitalizeFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
} 