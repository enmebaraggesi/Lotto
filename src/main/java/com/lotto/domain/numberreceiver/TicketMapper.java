package com.lotto.domain.numberreceiver;

import com.lotto.domain.numberreceiver.dto.TicketDto;

class TicketMapper {
    
    static TicketDto mapTicketToTicketDto(Ticket ticket) {
        return TicketDto.builder()
                        .numbersFromUser(ticket.getUserNumbers())
                        .drawDate(ticket.getDrawDate())
                        .ticketId(ticket.getId())
                        .build();
    }
}
