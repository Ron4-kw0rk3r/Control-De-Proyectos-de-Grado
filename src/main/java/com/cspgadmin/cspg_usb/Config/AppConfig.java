package com.cspgadmin.cspg_usb.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

// se importa reactive para usar webclient

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(8 * 1024 * 1024))
                        .build())
                .baseUrl("https://saf.usalesiana.edu.bo")
                //.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + "your_token_here")
                .build();
    }
}
