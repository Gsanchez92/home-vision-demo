package com.homevision.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient customWebClient() {
        HttpClient httpClient = HttpClient.create().followRedirect(true);

        return WebClient.builder()
                .baseUrl("http://app-homevision-staging.herokuapp.com/api_project/houses")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}