package com.lotto.domain.resultchecker;

import com.lotto.domain.numbergenerator.NumberGeneratorFacade;
import com.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import com.lotto.domain.numberreceiver.NumberReceiverFacade;
import com.lotto.domain.numberreceiver.dto.TicketDto;
import com.lotto.domain.resultchecker.dto.ResultDto;
import com.lotto.domain.resultchecker.dto.WinnersDto;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class ResultCheckerFacade {
    
    private final NumberGeneratorFacade numGenFacade;
    private final NumberReceiverFacade numRecFacade;
    private final PlayerLotsRepository repository;
    private final ResultCalculator calculator;
    
    public WinnersDto generateWinners() {
        WinningNumbersDto winningNumbersDto = numGenFacade.generateWinningNumbers();
        Set<Integer> winningNumbers = winningNumbersDto.winningNumbers();
        LocalDateTime drawDate = winningNumbersDto.date();
        List<TicketDto> ticketDtos = numRecFacade.findAllTicketsByNextDrawDate(drawDate);
        if (ticketDtos.isEmpty()) {
            return WinnersDto.builder()
                             .message("Winners failed to retrieve")
                             .build();
        }
        List<Ticket> tickets = ticketDtos.stream()
                                         .map(WinnersMapper::mapTicketDtoToTicket)
                                         .toList();
        List<PlayerLot> playerLots = calculator.retrieveWinners(tickets, winningNumbers);
        playerLots.forEach(repository::save);
        List<ResultDto> resultDtos = playerLots.stream()
                                               .map(WinnersMapper::mapPlayerLotToResultDto)
                                               .toList();
        return WinnersDto.builder()
                         .results(resultDtos)
                         .message("Winners succeeded to retrieve")
                         .build();
    }
    
    public ResultDto findById(final String id) {
        PlayerLot playerLot = repository.findById(id)
                                        .orElseThrow(() -> new RuntimeException("Not found"));
        return WinnersMapper.mapPlayerLotToResultDto(playerLot);
    }
}
