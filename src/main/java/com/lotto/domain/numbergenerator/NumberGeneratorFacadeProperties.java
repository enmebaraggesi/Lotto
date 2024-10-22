package com.lotto.domain.numbergenerator;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lotto.number-generator.facade")
public record NumberGeneratorFacadeProperties(int lowerBand,
                                              int upperBand,
                                              int count) {
    
}
