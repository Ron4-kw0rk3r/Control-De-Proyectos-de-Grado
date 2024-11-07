package com.cspgadmin.cspg_usb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
	"com.cspgadmin.cspg_usb.Controller", 
	"com.cspgadmin.cspg_usb.Service", 
	"com.cspgadmin.cspg_usb.Config",
	"com.cspgadmin.cspg_usb.session"
})
@EntityScan(basePackages = {"com.cspgadmin.cspg_usb.Model"})
@EnableJpaRepositories(basePackages = {"com.cspgadmin.cspg_usb.Repository"})
public class CspgUsbApplication {
	public static void main(String[] args) {
		SpringApplication.run(CspgUsbApplication.class, args);
	}
}
