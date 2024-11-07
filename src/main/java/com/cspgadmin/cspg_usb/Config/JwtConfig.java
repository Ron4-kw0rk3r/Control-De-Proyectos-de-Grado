package com.cspgadmin.cspg_usb.Config;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.security.Key;

@Configuration
public class JwtConfig {
    
    @Value("${jwt.secret:defaultSecretKey}")
    private String secret;
    
    @Value("${jwt.expiration:86400000}")
    private long expiration;
    
    @Bean
    public Key jwtKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    public long getExpiration() {
        return expiration;
    }
} 