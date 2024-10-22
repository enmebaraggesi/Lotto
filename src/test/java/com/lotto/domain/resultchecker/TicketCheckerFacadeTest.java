package com.lotto.domain.resultchecker;

import com.lotto.domain.numbergenerator.NumberGeneratorFacade;
import com.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import com.lotto.domain.numberreceiver.NumberReceiverFacade;
import com.lotto.domain.numberreceiver.dto.TicketDto;
import com.lotto.domain.resultchecker.dto.ResultDto;
import com.lotto.domain.resultchecker.dto.WinnersDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketCheckerFacadeTest {
    
    private final PlayerLotsRepository repository = new PlayerRepositoryTestImpl();
    private final NumberGeneratorFacade numberGeneratorFacade = mock(NumberGeneratorFacade.class);
    private final NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
    
    @Test
    public void it_should_generate_all_players_with_correct_message() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 1, 6, 12, 0, 0);
        when(numberGeneratorFacade.generateWinningNumbers()).thenReturn(
                new WinningNumbersDto(Set.of(1, 2, 3, 4, 5, 6), drawDate));
        when(numberReceiverFacade.findAllTicketsByNextDrawDate(drawDate)).thenReturn(
                List.of(TicketDto.builder()
                                 .ticketId("001")
                                 .numbersFromUser(Set.of(1, 2, 3, 4, 5, 6))
                                 .drawDate(drawDate)
                                 .build(),
                        TicketDto.builder()
                                 .ticketId("002")
                                 .numbersFromUser(Set.of(1, 2, 3, 8, 9, 10))
                                 .drawDate(drawDate)
                                 .build(),
                        TicketDto.builder()
                                 .ticketId("003")
                                 .numbersFromUser(Set.of(7, 8, 9, 10, 11, 12))
                                 .drawDate(drawDate)
                                 .build())
        );
        ResultCheckerFacade facade = new ResultCheckerConfiguration().resultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, repository);
        //when
        WinnersDto winnersDto = facade.generateWinners();
        //then
        List<ResultDto> results = winnersDto.results();
        ResultDto resultDto = ResultDto.builder()
                                       .id("001")
                                       .numbers(Set.of(1, 2, 3, 4, 5, 6))
                                       .hitNumbers(Set.of(1, 2, 3, 4, 5, 6))
                                       .drawDate(drawDate)
                                       .isWinner(true)
                                       .build();
        ResultDto resultDto1 = ResultDto.builder()
                                        .id("002")
                                        .numbers(Set.of(1, 2, 3, 8, 9, 10))
                                        .hitNumbers(Set.of(1, 2, 3))
                                        .drawDate(drawDate)
                                        .isWinner(true)
                                        .build();
        ResultDto resultDto2 = ResultDto.builder()
                                        .id("003")
                                        .numbers(Set.of(7, 8, 9, 10, 11, 12))
                                        .hitNumbers(Set.of())
                                        .drawDate(drawDate)
                                        .isWinner(false)
                                        .build();
        assertThat(results).contains(resultDto, resultDto1, resultDto2);
        String message = winnersDto.message();
        assertThat(message).isEqualTo("Winners succeeded to retrieve");
        
    }
    
    @Test
    public void it_should_generate_fail_message_when_winningNumbers_equal_null() {
        //given
        when(numberGeneratorFacade.generateWinningNumbers()).thenReturn(new WinningNumbersDto(null, null));
        ResultCheckerFacade facade = new ResultCheckerConfiguration().resultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, repository);
        //when
        WinnersDto winnersDto = facade.generateWinners();
        //then
        String message = winnersDto.message();
        assertThat(message).isEqualTo("Winners failed to retrieve");
        
    }
    
    @Test
    public void it_should_generate_fail_message_when_winningNumbers_is_empty() {
        //given
        when(numberGeneratorFacade.generateWinningNumbers()).thenReturn(new WinningNumbersDto(Collections.emptySet(), null));
        ResultCheckerFacade facade = new ResultCheckerConfiguration().resultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, repository);
        //when
        WinnersDto winnersDto = facade.generateWinners();
        //then
        String message = winnersDto.message();
        assertThat(message).isEqualTo("Winners failed to retrieve");
        
    }
    
    @Test
    public void it_should_generate_result_with_correct_credentials() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 1, 6, 12, 0, 0);
        when(numberGeneratorFacade.generateWinningNumbers()).thenReturn(new WinningNumbersDto(Set.of(1, 2, 3, 4, 5, 6), drawDate));
        String id = "001";
        when(numberReceiverFacade.findAllTicketsByNextDrawDate(drawDate)).thenReturn(
                List.of(TicketDto.builder()
                                 .ticketId(id)
                                 .numbersFromUser(Set.of(7, 8, 9, 10, 11, 12))
                                 .drawDate(drawDate)
                                 .build(),
                        TicketDto.builder()
                                 .ticketId("002")
                                 .numbersFromUser(Set.of(7, 8, 9, 10, 11, 13))
                                 .drawDate(drawDate)
                                 .build(),
                        TicketDto.builder()
                                 .ticketId("003")
                                 .numbersFromUser(Set.of(7, 8, 9, 10, 11, 14))
                                 .drawDate(drawDate)
                                 .build())
        );
        ResultCheckerFacade facade = new ResultCheckerConfiguration().resultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, repository);
        facade.generateWinners();
        //when
        ResultDto resultDto = facade.findById(id);
        //then
        ResultDto expectedResult = ResultDto.builder()
                                            .id(id)
                                            .numbers(Set.of(7, 8, 9, 10, 11, 12))
                                            .hitNumbers(Set.of())
                                            .drawDate(drawDate)
                                            .isWinner(false)
                                            .build();
        assertThat(resultDto).isEqualTo(expectedResult);
    }
}