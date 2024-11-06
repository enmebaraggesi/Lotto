package com.lotto.domain.numberreceiver;

import com.lotto.domain.numberreceiver.dto.InputNumbersResultDto;
import com.lotto.domain.numberreceiver.dto.TicketDto;
import com.lotto.domain.resultchecker.error.ResultNotFoundException;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class NumberReceiverFacade {
    
    private final NumberValidator validator;
    private final TicketRepository ticketRepository;
    private final DrawDateGenerator drawDateGenerator;
    
    public InputNumbersResultDto inputNumbers(Set<Integer> userNumbers) {
        List<ValidationResult> validate = validator.validate(userNumbers);
        if (!validate.isEmpty()) {
            String message = validator.generateMessage();
            return new InputNumbersResultDto(null, message);
        }
        LocalDateTime drawDate = drawDateGenerator.getNextDrawDate();
        Ticket ticket = ticketRepository.save(Ticket.builder()
                                                    .userNumbers(userNumbers)
                                                    .drawDate(drawDate)
                                                    .build());
        TicketDto ticketDto = TicketMapper.mapTicketToTicketDto(ticket);
        return new InputNumbersResultDto(ticketDto, ValidationResult.SUCCESS.message);
    }
    
    public List<TicketDto> findAllTicketsByNextDrawDate(LocalDateTime date) {
        List<Ticket> tickets = ticketRepository.findAllTicketsByDrawDate(date);
        return tickets.stream()
                      .map(TicketMapper::mapTicketToTicketDto)
                      .toList();
    }
    
    public LocalDateTime retrieveNextDrawDate() {
        return drawDateGenerator.getNextDrawDate();
    }
    
    public TicketDto findById(String id) {
        return ticketRepository.findById(id)
                               .map(TicketMapper::mapTicketToTicketDto)
                               .orElseThrow(() -> new ResultNotFoundException("Given ID: " + id + " does not exist"));
    }
}
