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
    private final TicketRepository repository;
    private final Clock clock;
    private final IdGenerable idGenerator;
    
    public InputNumbersResultDto inputNumbers(Set<Integer> userNumbers) {
        List<ValidationResult> validate = validator.validate(userNumbers);
        if (!validate.isEmpty()) {
            String message = validator.generateMessage();
            return new InputNumbersResultDto(null, message);
        }
        String id = idGenerator.generateId();
        LocalDateTime drawDate = LocalDateTime.now(clock);
        
        Ticket ticket = repository.save(new Ticket(id, drawDate, userNumbers));
        
        TicketDto ticketDto = TicketMapper.mapTicketToTicketDto(ticket);
        return new InputNumbersResultDto(ticketDto, ValidationResult.SUCCESS.message);
    }
    
    public List<TicketDto> findAllTicketsByNextDrawDate(LocalDateTime date) {
        List<Ticket> tickets = repository.findAllTicketsByDrawDate(date);
        return tickets.stream()
                      .map(TicketMapper::mapTicketToTicketDto)
                      .toList();
    }
}
