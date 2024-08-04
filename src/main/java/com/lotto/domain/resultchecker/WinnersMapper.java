package com.lotto.domain.resultchecker;

import com.lotto.domain.numberreceiver.dto.TicketDto;
import com.lotto.domain.resultchecker.dto.ResultDto;

class WinnersMapper {
    
    static Ticket mapTicketDtoToTicket(TicketDto ticketDto) {
        return Ticket.builder()
                     .id(ticketDto.ticketId())
                     .numbers(ticketDto.numbersFromUser())
                     .drawDate(ticketDto.drawDate())
                     .build();
    }
    
    static ResultDto mapPlayerLotToResultDto(PlayerLot playerLot) {
        return ResultDto.builder()
                        .id(playerLot.id())
                        .numbers(playerLot.numbers())
                        .hitNumbers(playerLot.hitNumbers())
                        .drawDate(playerLot.drawDate())
                        .isWinner(playerLot.isWinner())
                        .build();
    }
}
