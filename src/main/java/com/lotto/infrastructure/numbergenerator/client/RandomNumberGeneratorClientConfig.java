package com.lotto.infrastructure.numbergenerator.client;

import com.lotto.domain.numbergenerator.RandomNumberGenerable;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
class RandomNumberGeneratorClientConfig {
    
    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }
    
    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(1000))
                .setReadTimeout(Duration.ofMillis(1000))
                .build();
    }
    
    @Bean
    public RandomNumberGenerable randomNumberGeneratorClient(RestTemplate restTemplate) {
        return new RandomNumberGeneratorClient(restTemplate);
    }
}
