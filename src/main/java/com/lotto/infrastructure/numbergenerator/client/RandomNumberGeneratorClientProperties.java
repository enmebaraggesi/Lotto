package com.lotto.infrastructure.numbergenerator.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lotto.number-generator.http.client")
public record RandomNumberGeneratorClientProperties(String uri,
                                                    String service,
                                                    int port,
                                                    int connectionTimeout,
                                                    int readTimeout) {
    
}
