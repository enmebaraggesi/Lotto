package com.lotto.domain.numberreceiver;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class NumberReceiverFacade {
    
    private final NumberValidator validator;
    private final NumberReceiverRepository repository;
    
    public InputNumbersResultDto inputNumbers(Set<Integer> userNumbers) {
        if (validator.filterAllNumbersInRange(userNumbers)) {
            String id = UUID.randomUUID().toString();
            LocalDateTime drawDate = LocalDateTime.now();
            Ticket ticket = repository.save(new Ticket(id, drawDate, userNumbers));
            return InputNumbersResultDto.builder()
                                        .message("success")
                                        .drawDate(ticket.drawDate)
                                        .ticketId(ticket.id)
                                        .build();
        }
        return InputNumbersResultDto.builder()
                                    .message("failure")
                                    .build();
    }
    
    public List<TicketDto> userNumbers(LocalDateTime date) {
        List<Ticket> tickets = repository.findAllTicketsByDrawDate(date);
        return tickets.stream()
                      .map(TicketMapper::mapTicketToTicketDto)
                      .toList();
    }
}
