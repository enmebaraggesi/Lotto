package com.lotto.infrastructure.numbergenerator.client;

import com.lotto.domain.numbergenerator.RandomNumberGenerable;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@AllArgsConstructor
public class RandomNumberGeneratorClientConfig {
    
    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }
    
    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler, RandomNumberGeneratorClientProperties properties) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(properties.connectionTimeout()))
                .setReadTimeout(Duration.ofMillis(properties.readTimeout()))
                .build();
    }
    
    @Bean
    public RandomNumberGenerable randomNumberGeneratorClient(RestTemplate restTemplate, RandomNumberGeneratorClientProperties properties) {
        return new RandomNumberGeneratorClient(restTemplate, properties);
    }
}
