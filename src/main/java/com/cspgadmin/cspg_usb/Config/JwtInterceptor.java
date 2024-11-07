package com.cspgadmin.cspg_usb.Config;

import com.cspgadmin.cspg_usb.Service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        
        // Permitir acceso a recursos públicos
        if (isPublicResource(path)) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no proporcionado");
            return false;
        }

        token = token.substring(7);
        if (!jwtService.validateToken(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
            return false;
        }

        String rol = jwtService.getRolFromToken(token);
        if (path.startsWith("/admin/") && !isAdminRole(rol)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado");
            return false;
        }

        return true;
    }

    private boolean isPublicResource(String path) {
        return path.equals("/") ||
               path.equals("/index") ||
               path.equals("/login") ||
               path.equals("/error") ||
               path.startsWith("/static/") ||
               path.startsWith("/css/") ||
               path.startsWith("/js/") ||
               path.startsWith("/images/");
    }

    private boolean isAdminRole(String rol) {
        return "ADMIN".equals(rol) || "ROOT_ADMIN".equals(rol);
    }
} 