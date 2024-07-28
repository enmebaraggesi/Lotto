package com.lotto.domain.numberreceiver;

import com.lotto.domain.numberreceiver.dto.InputNumbersResultDto;
import com.lotto.domain.numberreceiver.dto.TicketDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class NumberReceiverFacadeTest {
    
    AdjustableClock clock = new AdjustableClock(LocalDateTime.of(2024, 1, 1, 1, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));
    
    NumberReceiverFacade facade = new NumberReceiverFacade(
            new NumberValidator(),
            new InMemoryNumberReceiverRepositoryImpl(),
            clock,
            new IdGenerableImpl()
    );
    
    @Test
    public void should_save_to_database_when_user_give_six_numbers() {
        //given
        Set<Integer> userNumbers = Set.of(1, 2, 3, 4, 5, 6);
        //when
        InputNumbersResultDto resultDto = facade.inputNumbers(userNumbers);
        //then
        assertThat(resultDto.ticket()).isNotNull();
        assertThat(resultDto.ticket()).isEqualTo(TicketDto.builder()
                                                          .ticketId("ABCD")
                                                          .numbersFromUser(userNumbers)
                                                          .drawDate(LocalDateTime.of(2024, 1, 1, 1, 0, 0))
                                                          .build()
        );
        assertThat(resultDto.message()).isNotNull();
        assertThat(resultDto.message()).isEqualTo(ValidationResult.SUCCESS.message);
    }
    
    @Test
    public void should_return_fail_when_user_give_less_than_six_numbers() {
        //given
        Set<Integer> userNumbers = Set.of(1, 2, 3, 4, 5);
        //when
        InputNumbersResultDto resultDto = facade.inputNumbers(userNumbers);
        //then
        assertThat(resultDto.message()).isNotNull();
        assertThat(resultDto.message()).isEqualTo(ValidationResult.WRONG_NUMBERS_QUANTITY.message);
    }
    
    @Test
    public void should_return_fail_when_user_give_more_than_six_numbers() {
        //given
        Set<Integer> userNumbers = Set.of(1, 2, 3, 4, 5, 6, 7);
        //when
        InputNumbersResultDto resultDto = facade.inputNumbers(userNumbers);
        //then
        assertThat(resultDto.message()).isNotNull();
        assertThat(resultDto.message()).isEqualTo(ValidationResult.WRONG_NUMBERS_QUANTITY.message);
    }
    
    @Test
    public void should_return_fail_when_user_give_at_least_one_number_out_of_range() {
        //given
        Set<Integer> userNumbers = Set.of(1, 200, 3, 4, 5, 6);
        //when
        InputNumbersResultDto resultDto = facade.inputNumbers(userNumbers);
        //then
        assertThat(resultDto.message()).isNotNull();
        assertThat(resultDto.message()).isEqualTo(ValidationResult.NOT_IN_RANGE.message);
    }
    
    @Test
    public void should_return_fail_when_user_give_at_least_one_negative_number() {
        //given
        Set<Integer> userNumbers = Set.of(1, 2, -3, 4, 5, 6);
        //when
        InputNumbersResultDto resultDto = facade.inputNumbers(userNumbers);
        //then
        assertThat(resultDto.message()).isNotNull();
        assertThat(resultDto.message()).isEqualTo(ValidationResult.NOT_IN_RANGE.message);
    }
}