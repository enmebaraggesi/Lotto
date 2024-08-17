package com.lotto;

import com.lotto.domain.numbergenerator.NumberGeneratorFacadeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({NumberGeneratorFacadeProperties.class})
public class LottoSpringBootApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(LottoSpringBootApplication.class, args);
    }
}
