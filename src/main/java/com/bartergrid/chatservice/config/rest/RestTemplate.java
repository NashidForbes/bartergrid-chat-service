package com.bartergrid.chatservice.config.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestTemplate {

    @Value("${server.port}")
    private int serverPort;

    @Bean
    public org.springframework.web.client.RestTemplate myRestTemplate() {
        return new RestTemplateBuilder()
                .rootUri("http://localhost:" + serverPort)
                .build();
    }
}