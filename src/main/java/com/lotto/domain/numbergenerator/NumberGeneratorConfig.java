package com.lotto.domain.numbergenerator;

import com.lotto.domain.numberreceiver.NumberReceiverFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
public class NumberGeneratorConfig {
    
    @Bean
    WinningNumbersRepository winningNumbersRepository() {
        return new WinningNumbersRepository() {
            @Override
            public WinningNumbers save(final WinningNumbers winningNumbers) {
                return null;
            }
            
            @Override
            public Optional<WinningNumbers> findWinningNumberByDate(final LocalDateTime drawDate) {
                return Optional.empty();
            }
            
            @Override
            public boolean existsByDate(final LocalDateTime drawDate) {
                return false;
            }
        };
    }
    
    @Bean
    NumberGeneratorFacade numberGeneratorFacade(RandomNumberGenerable randomGenerator, WinningNumbersRepository repository, NumberReceiverFacade numberReceiverFacade, NumberGeneratorFacadeProperties properties) {
        GeneratedNumberValidator validator = new GeneratedNumberValidator();
        return new NumberGeneratorFacade(randomGenerator, repository, numberReceiverFacade, validator, properties);
    }
}
