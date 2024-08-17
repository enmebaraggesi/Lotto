package com.lotto;

import com.lotto.domain.numbergenerator.NumberGeneratorFacadeProperties;
import com.lotto.infrastructure.numbergenerator.client.RandomNumberGeneratorClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({NumberGeneratorFacadeProperties.class, RandomNumberGeneratorClientProperties.class})
public class LottoSpringBootApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(LottoSpringBootApplication.class, args);
    }
}
