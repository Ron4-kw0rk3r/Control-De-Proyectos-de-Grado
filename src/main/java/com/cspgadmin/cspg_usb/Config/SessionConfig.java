package com.cspgadmin.cspg_usb.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfig {
    
    @Bean
    public SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor();
    }
} 