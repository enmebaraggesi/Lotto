package com.lotto.domain.numberreceiver;

class TicketMapper {
    
    static TicketDto mapTicketToTicketDto(Ticket ticket) {
        return TicketDto.builder()
                        .numbersFromUser(ticket.getUserNumbers())
                        .drawDate(ticket.getDrawDate())
                        .ticketId(ticket.getId())
                        .build();
    }
}
