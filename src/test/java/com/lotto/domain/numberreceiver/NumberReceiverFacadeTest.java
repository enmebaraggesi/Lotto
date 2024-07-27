package com.lotto.domain.numberreceiver;

import com.lotto.domain.numberreceiver.dto.InputNumbersResultDto;
import com.lotto.domain.numberreceiver.dto.TicketDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class NumberReceiverFacadeTest {
    
    AdjustableClock clock = new AdjustableClock(LocalDateTime.of(2024, 6, 15, 12, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));
    
    NumberReceiverFacade facade = new NumberReceiverFacade(
            new NumberValidator(),
            new InMemoryNumberReceiverRepositoryImpl(),
            clock
    );
    
    
    @Test
    public void should_save_to_database_when_user_give_six_numbers() {
        //given
        Set<Integer> userNumbers = Set.of(1, 2, 3, 4, 5, 6);
        InputNumbersResultDto result = facade.inputNumbers(userNumbers);
        LocalDateTime drawDate = LocalDateTime.of(2024, 6, 15, 12, 0, 0);
        //when
        List<TicketDto> ticketDtos = facade.userNumbers(drawDate);
        //then
        assertThat(ticketDtos).contains(
                TicketDto.builder()
                         .ticketId(result.ticketId())
                         .drawDate(drawDate)
                         .numbersFromUser(userNumbers)
                         .build()
        );
    }
    
    @Test
    public void should_return_fail_when_user_give_less_than_six_numbers() {
        //given
        Set<Integer> userNumbers = Set.of(1, 2, 3, 4, 5);
        //when
        InputNumbersResultDto result = facade.inputNumbers(userNumbers);
        //then
        assertThat(result.message()).isEqualTo("failure");
    }
    
    @Test
    public void should_return_fail_when_user_give_more_than_six_numbers() {
        //given
        Set<Integer> userNumbers = Set.of(1, 2, 3, 4, 5, 6, 7);
        //when
        InputNumbersResultDto result = facade.inputNumbers(userNumbers);
        //then
        assertThat(result.message()).isEqualTo("failure");
    }
    
    @Test
    public void should_return_fail_when_user_give_at_least_one_number_out_of_range() {
        //given
        Set<Integer> userNumbers = Set.of(1, 200, 3, 4, 5, 6);
        //when
        InputNumbersResultDto result = facade.inputNumbers(userNumbers);
        //then
        assertThat(result.message()).isEqualTo("failure");
    }
}