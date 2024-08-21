package com.lotto.domain.numbergenerator;

import com.lotto.domain.numberreceiver.NumberReceiverFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NumberGeneratorConfig {
    
    @Bean
    NumberGeneratorFacade numberGeneratorFacade(RandomNumberGenerable randomGenerator, WinningNumbersRepository repository, NumberReceiverFacade numberReceiverFacade, NumberGeneratorFacadeProperties properties) {
        GeneratedNumberValidator validator = new GeneratedNumberValidator();
        return new NumberGeneratorFacade(randomGenerator, repository, numberReceiverFacade, validator, properties);
    }
}
