package com.lotto.domain.numberreceiver;

import com.lotto.domain.numberreceiver.dto.InputNumbersResultDto;
import com.lotto.domain.numberreceiver.dto.TicketDto;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class NumberReceiverFacade {
    
    private final NumberValidator validator;
    private final NumberReceiverRepository repository;
    private final Clock clock;
    private final IdGenerable idGenerator;
    
    public InputNumbersResultDto inputNumbers(Set<Integer> userNumbers) {
        if (validator.filterAllNumbersInRange(userNumbers)) {
            String id = idGenerator.generateId();
            LocalDateTime drawDate = LocalDateTime.now(clock);
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
    
    public List<TicketDto> findAllTicketsByNextDrawDate(LocalDateTime date) {
        List<Ticket> tickets = repository.findAllTicketsByDrawDate(date);
        return tickets.stream()
                      .map(TicketMapper::mapTicketToTicketDto)
                      .toList();
    }
}
