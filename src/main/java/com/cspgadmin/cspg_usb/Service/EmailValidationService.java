package com.cspgadmin.cspg_usb.Service;

import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class EmailValidationService {
    
    private static final String DOMAIN = "usalesiana.edu.bo";
    private static final String STUDENT_PATTERN = "^.*@" + DOMAIN + "$";
    private static final String TEACHER_PATTERN = "^.*@services\\." + DOMAIN + "$";
    private static final String ROOT_PATTERN = "^.*@root\\." + DOMAIN + "$";
    private static final String ADMIN_PATTERN = "^.*@admin\\." + DOMAIN + "$";

    public String determineUserRole(String email) {
        if (Pattern.matches(STUDENT_PATTERN, email)) {
            return "ESTUDIANTE";
        } else if (Pattern.matches(TEACHER_PATTERN, email)) {
            return "DOCENTE";
        } else if (Pattern.matches(ROOT_PATTERN, email)) {
            return "ROOT_ADMIN";
        } else if (Pattern.matches(ADMIN_PATTERN, email)) {
            return "ADMIN";
        }
        return null;
    }

    public boolean isValidEmail(String email) {
        return determineUserRole(email) != null;
    }
} 