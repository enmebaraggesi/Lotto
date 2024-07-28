package com.lotto.domain.numberreceiver;

import com.lotto.domain.numberreceiver.dto.InputNumbersResultDto;
import com.lotto.domain.numberreceiver.dto.TicketDto;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class NumberReceiverFacadeTest {
    
    IdGenerableImpl idGenerator = new IdGenerableImpl();
    TicketRepositoryImpl repository = new TicketRepositoryImpl();
    Clock clock = Clock.systemUTC();
    
    @Test
    public void should_return_correct_response_when_user_give_six_numbers_in_range() {
        //given
        NumberReceiverFacade facade = new NumberRecieverConfig().forTests(repository, clock, idGenerator);
        Set<Integer> userNumbers = Set.of(1, 2, 3, 4, 5, 6);
        //when
        InputNumbersResultDto resultDto = facade.inputNumbers(userNumbers);
        LocalDateTime nextDrawDate = new DrawDateGenerator(clock).getNextDrawDate();
        //then
        assertThat(resultDto.ticket()).isNotNull();
        assertThat(resultDto.ticket().ticketId()).isEqualTo(idGenerator.generateId());
        assertThat(resultDto.ticket().numbersFromUser()).isEqualTo(userNumbers);
        assertThat(resultDto.ticket().drawDate()).isEqualTo(nextDrawDate);
        assertThat(resultDto.message()).isNotNull();
        assertThat(resultDto.message()).isEqualTo(ValidationResult.SUCCESS.message);
    }
    
    @Test
    public void should_return_fail_when_user_give_less_than_six_numbers() {
        //given
        NumberReceiverFacade facade = new NumberRecieverConfig().forTests(repository, clock, idGenerator);
        Set<Integer> userNumbersNotMuch = Set.of(1, 2, 3, 4, 5);
        //when
        InputNumbersResultDto resultDto = facade.inputNumbers(userNumbersNotMuch);
        //then
        assertThat(resultDto.message()).isNotNull();
        assertThat(resultDto.message()).isEqualTo(ValidationResult.WRONG_NUMBERS_QUANTITY.message);
    }
    
    @Test
    public void should_return_fail_when_user_give_more_than_six_numbers() {
        //given
        NumberReceiverFacade facade = new NumberRecieverConfig().forTests(repository, clock, idGenerator);
        Set<Integer> userNumbersTooMany = Set.of(1, 2, 3, 4, 5, 6, 7);
        //when
        InputNumbersResultDto resultDto = facade.inputNumbers(userNumbersTooMany);
        //then
        assertThat(resultDto.message()).isNotNull();
        assertThat(resultDto.message()).isEqualTo(ValidationResult.WRONG_NUMBERS_QUANTITY.message);
    }
    
    @Test
    public void should_return_fail_when_user_give_at_least_one_number_out_of_range() {
        //given
        NumberReceiverFacade facade = new NumberRecieverConfig().forTests(repository, clock, idGenerator);
        Set<Integer> userNumbersWithOutOfRange = Set.of(1, 200, 3, 4, 5, 6);
        //when
        InputNumbersResultDto resultDto = facade.inputNumbers(userNumbersWithOutOfRange);
        //then
        assertThat(resultDto.message()).isNotNull();
        assertThat(resultDto.message()).isEqualTo(ValidationResult.NOT_IN_RANGE.message);
    }
    
    @Test
    public void should_return_fail_when_user_give_at_least_one_negative_number() {
        //given
        NumberReceiverFacade facade = new NumberRecieverConfig().forTests(repository, clock, idGenerator);
        Set<Integer> userNumbersWithNegative = Set.of(1, 2, -3, 4, 5, 6);
        //when
        InputNumbersResultDto resultDto = facade.inputNumbers(userNumbersWithNegative);
        //then
        assertThat(resultDto.message()).isNotNull();
        assertThat(resultDto.message()).isEqualTo(ValidationResult.NOT_IN_RANGE.message);
    }
    
    @Test
    public void should_return_correct_generated_id() {
        //given
        IdGenerator generator = new IdGenerator();
        NumberReceiverFacade facade = new NumberRecieverConfig().forTests(repository, clock, generator);
        //when
        InputNumbersResultDto resultDto = facade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6));
        //then
        assertThat(resultDto.ticket().ticketId()).isNotNull();
        assertThat(resultDto.ticket().ticketId()).hasSize(36);
    }
    
    @Test
    public void should_return_next_saturday_draw_date_when_current_date_is_days_before() {
        //given
        Clock fixedClock = Clock.fixed(LocalDateTime.of(2024, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));
        NumberReceiverFacade facade = new NumberRecieverConfig().forTests(repository, fixedClock, idGenerator);
        //when
        LocalDateTime drawDate = facade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6)).ticket().drawDate();
        //then
        LocalDateTime expectedDrawDate = LocalDateTime.of(2024, 1, 6, 12, 0, 0);
        assertThat(drawDate).isNotNull();
        assertThat(drawDate).isEqualTo(expectedDrawDate);
    }
    
    @Test
    public void should_return_next_saturday_draw_date_when_current_date_is_saturday_morning() {
        //given
        Clock fixedClock = Clock.fixed(LocalDateTime.of(2024, 1, 6, 11, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));
        NumberReceiverFacade facade = new NumberRecieverConfig().forTests(repository, fixedClock, idGenerator);
        //when
        LocalDateTime drawDate = facade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6)).ticket().drawDate();
        //then
        LocalDateTime expectedDrawDate = LocalDateTime.of(2024, 1, 6, 12, 0, 0);
        assertThat(drawDate).isNotNull();
        assertThat(drawDate).isEqualTo(expectedDrawDate);
    }
    
    @Test
    public void should_return_next_saturday_draw_date_when_current_date_is_saturday_noon() {
        //given
        Clock fixedClock = Clock.fixed(LocalDateTime.of(2024, 1, 6, 12, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));
        NumberReceiverFacade facade = new NumberRecieverConfig().forTests(repository, fixedClock, idGenerator);
        //when
        LocalDateTime drawDate = facade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6)).ticket().drawDate();
        //then
        LocalDateTime expectedDrawDate = LocalDateTime.of(2024, 1, 13, 12, 0, 0);
        assertThat(drawDate).isNotNull();
        assertThat(drawDate).isEqualTo(expectedDrawDate);
    }
    
    @Test
    public void should_return_next_saturday_draw_date_when_current_date_is_saturday_afternoon() {
        //given
        Clock fixedClock = Clock.fixed(LocalDateTime.of(2024, 1, 6, 16, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));
        NumberReceiverFacade facade = new NumberRecieverConfig().forTests(repository, fixedClock, idGenerator);
        //when
        LocalDateTime drawDate = facade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6)).ticket().drawDate();
        //then
        LocalDateTime expectedDrawDate = LocalDateTime.of(2024, 1, 13, 12, 0, 0);
        assertThat(drawDate).isNotNull();
        assertThat(drawDate).isEqualTo(expectedDrawDate);
    }
    
    @Test
    public void should_return_empty_list_when_there_is_no_tickets() {
        //given
        NumberReceiverFacade facade = new NumberRecieverConfig().forTests(repository, clock, idGenerator);
        LocalDateTime nextDrawDate = new DrawDateGenerator(clock).getNextDrawDate();
        //when
        List<TicketDto> resultDtos = facade.findAllTicketsByNextDrawDate(nextDrawDate);
        //then
        assertThat(resultDtos).isEmpty();
    }
    
    @Test
    public void should_return_list_of_tickets_when_there_are_some_and_draw_date_is_correct() {
        //given
        Clock fixedClock1 = Clock.fixed(LocalDateTime.of(2024, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));
        IdGenerator generator = new IdGenerator();
        NumberReceiverFacade facade1 = new NumberRecieverConfig().forTests(repository, fixedClock1, generator);
        TicketDto ticket1 = facade1.inputNumbers(Set.of(1, 2, 3, 4, 5, 6)).ticket();
        TicketDto ticket2 = facade1.inputNumbers(Set.of(1, 2, 3, 4, 5, 6)).ticket();
        Clock fixedClock2 = Clock.fixed(LocalDateTime.of(2024, 1, 7, 12, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));
        NumberReceiverFacade facade2 = new NumberRecieverConfig().forTests(repository, fixedClock2, generator);
        facade2.inputNumbers(Set.of(1, 2, 3, 4, 5, 6));
        LocalDateTime drawDate = new DrawDateGenerator(fixedClock1).getNextDrawDate();
        //when
        List<TicketDto> resultDtos = facade2.findAllTicketsByNextDrawDate(drawDate);
        //then
        assertThat(resultDtos).isNotEmpty();
        assertThat(resultDtos).hasSize(2);
        assertThat(resultDtos).containsOnly(ticket1, ticket2);
    }
    
    @Test
    public void should_return_empty_list_when_there_are_tickets_but_draw_date_is_incorrect() {
        //given
        Clock fixedClock = Clock.fixed(LocalDateTime.of(2024, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));
        NumberReceiverFacade facade = new NumberRecieverConfig().forTests(repository, fixedClock, idGenerator);
        LocalDateTime drawDate = facade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6)).ticket().drawDate();
        //when
        List<TicketDto> resultDtos = facade.findAllTicketsByNextDrawDate(drawDate.plusWeeks(1L));
        //then
        assertThat(resultDtos).isEmpty();
    }
}