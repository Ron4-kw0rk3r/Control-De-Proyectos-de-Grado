package com.cspgadmin.cspg_usb.Service;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;


@Service
public class Serverone {

    private final WebClient webClient;

    public Serverone(WebClient webClient) {
        this.webClient = webClient;
    }

    public String registrarUsuario(String email) {
        try {
            Mono<String> responseMono = webClient.post()
                    .uri("/frida/aa/log/")
                    .bodyValue(email)
                    .retrieve()
                    .bodyToMono(String.class);



            String response = responseMono.block(); // Bloquea para obtener la respuesta sincrónicamente
            System.out.println("Respuesta del servicio: " + response);
            return response;
        } catch (WebClientResponseException e) {
            // Manejo de errores HTTP
            System.err.println("Error HTTP: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return "Error HTTP: " + e.getStatusCode();
        } catch (Exception e) {
            // Manejo de otros errores
            System.err.println("Error: " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }
}

