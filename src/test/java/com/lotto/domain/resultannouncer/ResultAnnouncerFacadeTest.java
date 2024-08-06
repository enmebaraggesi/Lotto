package com.lotto.domain.resultannouncer;

import com.lotto.domain.resultannouncer.dto.ResponseDto;
import com.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import com.lotto.domain.resultchecker.ResultCheckerFacade;
import com.lotto.domain.resultchecker.dto.ResultDto;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;

import static com.lotto.domain.resultannouncer.AnnouncementMessage.ALREADY_CHECKED;
import static com.lotto.domain.resultannouncer.AnnouncementMessage.ID_DOES_NOT_EXIST_MESSAGE;
import static com.lotto.domain.resultannouncer.AnnouncementMessage.LOSE_MESSAGE;
import static com.lotto.domain.resultannouncer.AnnouncementMessage.WAIT_MESSAGE;
import static com.lotto.domain.resultannouncer.AnnouncementMessage.WIN_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResultAnnouncerFacadeTest {
    
    ResponseRepository responseRepository = new ResponseRepositoryTestImpl();
    ResultCheckerFacade resultCheckerFacade = mock(ResultCheckerFacade.class);
    
    @Test
    public void it_should_return_response_with_lose_message_if_ticket_is_not_winning_ticket() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2022, 12, 17, 12, 0, 0);
        String id = "123";
        ResultAnnouncerFacade facade = new ResultAnnouncerConfiguration().createForTest(resultCheckerFacade, responseRepository, Clock.systemUTC());
        ResultDto resultDto = ResultDto.builder()
                                       .id(id)
                                       .numbers(Set.of(1, 2, 3, 4, 5, 6))
                                       .hitNumbers(Set.of())
                                       .drawDate(drawDate)
                                       .isWinner(false)
                                       .build();
        when(resultCheckerFacade.findById(id)).thenReturn(resultDto);
        //when
        ResultAnnouncerResponseDto response = facade.checkResult(id);
        //then
        ResponseDto responseDto = ResponseDto.builder()
                                             .id("123")
                                             .numbers(Set.of(1, 2, 3, 4, 5, 6))
                                             .hitNumbers(Set.of())
                                             .drawDate(drawDate)
                                             .isWinner(false)
                                             .build();
        ResultAnnouncerResponseDto expected = new ResultAnnouncerResponseDto(responseDto, LOSE_MESSAGE.message);
        assertThat(response).isEqualTo(expected);
    }
    
    @Test
    public void it_should_return_response_with_win_message_if_ticket_is_winning_ticket() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2022, 12, 17, 12, 0, 0);
        String id = "123";
        ResultAnnouncerFacade facade = new ResultAnnouncerConfiguration().createForTest(resultCheckerFacade, responseRepository, Clock.systemUTC());
        ResultDto resultDto = ResultDto.builder()
                                       .id(id)
                                       .numbers(Set.of(1, 2, 3, 4, 5, 6))
                                       .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                                       .drawDate(drawDate)
                                       .isWinner(true)
                                       .build();
        when(resultCheckerFacade.findById(id)).thenReturn(resultDto);
        //when
        ResultAnnouncerResponseDto response = facade.checkResult(id);
        //then
        ResponseDto responseDto = ResponseDto.builder()
                                             .id("123")
                                             .numbers(Set.of(1, 2, 3, 4, 5, 6))
                                             .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                                             .drawDate(drawDate)
                                             .isWinner(true)
                                             .build();
        ResultAnnouncerResponseDto expected = new ResultAnnouncerResponseDto(responseDto, WIN_MESSAGE.message);
        assertThat(response).isEqualTo(expected);
    }
    
    @Test
    public void it_should_return_response_with_wait_message_if_date_is_before_announcement_time() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2022, 12, 31, 12, 0, 0);
        String id = "123";
        Clock clock = Clock.fixed(LocalDateTime.of(2022, 12, 17, 12, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        ResultAnnouncerFacade facade = new ResultAnnouncerConfiguration().createForTest(resultCheckerFacade, responseRepository, clock);
        ResultDto resultDto = ResultDto.builder()
                                       .id(id)
                                       .numbers(Set.of(1, 2, 3, 4, 5, 6))
                                       .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                                       .drawDate(drawDate)
                                       .isWinner(true)
                                       .build();
        when(resultCheckerFacade.findById(id)).thenReturn(resultDto);
        //when
        ResultAnnouncerResponseDto response = facade.checkResult(id);
        //then
        ResponseDto responseDto = ResponseDto.builder()
                                             .id("123")
                                             .numbers(Set.of(1, 2, 3, 4, 5, 6))
                                             .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                                             .drawDate(drawDate)
                                             .isWinner(true)
                                             .build();
        ResultAnnouncerResponseDto expected = new ResultAnnouncerResponseDto(responseDto, WAIT_MESSAGE.message);
        assertThat(response).isEqualTo(expected);
    }
    
    @Test
    public void it_should_return_response_with_id_does_not_exist_message_if_id_does_not_exist() {
        //given
        String id = "123";
        ResultAnnouncerFacade facade = new ResultAnnouncerConfiguration().createForTest(resultCheckerFacade, responseRepository, Clock.systemUTC());
        when(resultCheckerFacade.findById(id)).thenReturn(null);
        //when
        ResultAnnouncerResponseDto response = facade.checkResult(id);
        //then
        ResultAnnouncerResponseDto expected = new ResultAnnouncerResponseDto(null, ID_DOES_NOT_EXIST_MESSAGE.message);
        assertThat(response).isEqualTo(expected);
    }
    
    @Test
    public void it_should_return_response_with_already_checked_if_response_exists_in_db() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2022, 12, 17, 12, 0, 0);
        String id = "123";
        ResultDto resultDto = ResultDto.builder()
                                       .id(id)
                                       .numbers(Set.of(1, 2, 3, 4, 5, 6))
                                       .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                                       .drawDate(drawDate)
                                       .isWinner(true)
                                       .build();
        when(resultCheckerFacade.findById(id)).thenReturn(resultDto);
        ResultAnnouncerFacade facade = new ResultAnnouncerConfiguration().createForTest(resultCheckerFacade, responseRepository, Clock.systemUTC());
        //when
        facade.checkResult(id);
        ResultAnnouncerResponseDto response = facade.checkResult(id);
        //then
        ResultAnnouncerResponseDto expected = new ResultAnnouncerResponseDto(response.responseDto(), ALREADY_CHECKED.message);
        assertThat(response).isEqualTo(expected);
    }
}