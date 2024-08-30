package com.lotto.domain.numberreceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class NumberRecieverConfig {
    
    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }
    
    @Bean
    IdGenerable idGenerable() {
        return new IdGenerator();
    }
    
    @Bean
    NumberReceiverFacade numberReceiverFacade(TicketRepository repository, Clock clock, IdGenerable idGenerator) {
        NumberValidator validator = new NumberValidator();
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(clock);
        return new NumberReceiverFacade(validator, repository, drawDateGenerator, idGenerator);
    }
}
