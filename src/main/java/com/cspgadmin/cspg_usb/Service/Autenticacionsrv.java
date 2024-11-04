package com.cspgadmin.cspg_usb.Service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class Autenticacionsrv {
    private final WebClient webClient;

    public Autenticacionsrv(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://saf.usalesiana.edu.bo").build();
    }

    public Mono<HttpStatus> authenticate() {
        return webClient.get()
                .uri("/frida/aa/log/") // Asegúrate de especificar el endpoint correcto
                .exchangeToMono(response -> handleResponse(response));
    }

    private Mono<HttpStatus> handleResponse(ClientResponse response) {
        if (response.statusCode().isError()) {
            return response.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new RuntimeException("Error: " + response.statusCode() + " - " + body)));
        } else {
            return Mono.just(HttpStatus.valueOf(response.statusCode().value()));    
        }
    }
}

