package com.example.gitrepoexplorer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${github.token:}")
    private String githubToken;

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        WebClient.Builder builder = webClientBuilder.baseUrl("https://api.github.com");

        if (!githubToken.isEmpty()) {
            builder.defaultHeader("Authorization", "Bearer " + githubToken);
        }

        return builder.build();
    }
}
