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
    NumberReceiverFacade numberReceiverFacade(TicketRepository repository, Clock clock) {
        NumberValidator validator = new NumberValidator();
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(clock);
        return new NumberReceiverFacade(validator, repository, drawDateGenerator);
    }
}
