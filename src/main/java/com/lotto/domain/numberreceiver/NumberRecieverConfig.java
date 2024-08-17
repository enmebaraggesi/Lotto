package com.lotto.domain.numberreceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

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
    TicketRepository ticketRepository() {
        return new TicketRepository() {
            @Override
            public Ticket save(final Ticket ticket) {
                return null;
            }
            
            @Override
            public List<Ticket> findAllTicketsByDrawDate(final LocalDateTime date) {
                return List.of();
            }
        };
    }
    
    @Bean
    NumberReceiverFacade numberReceiverFacade(TicketRepository repository, Clock clock, IdGenerable idGenerator) {
        NumberValidator validator = new NumberValidator();
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(clock);
        return new NumberReceiverFacade(validator, repository, drawDateGenerator, idGenerator);
    }
}
