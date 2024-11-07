package com.cspgadmin.cspg_usb.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        
        // Permitir acceso a recursos estáticos y páginas públicas
        if (isPublicResource(path)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("/index");
            return false;
        }

        String rol = (String) session.getAttribute("rol");
        if (path.startsWith("/admin/") && !isAdminRole(rol)) {
            response.sendRedirect("/error");
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