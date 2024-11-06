package com.lotto.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.lotto.BaseIntegrationTest;
import com.lotto.domain.numbergenerator.NumberGeneratorFacade;
import com.lotto.domain.numbergenerator.error.WinningNumbersNotFoundException;
import com.lotto.domain.numberreceiver.dto.InputNumbersResultDto;
import com.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import com.lotto.domain.resultchecker.ResultCheckerFacade;
import com.lotto.domain.resultchecker.dto.ResultDto;
import com.lotto.domain.resultchecker.error.ResultNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class UserPlayedAndWonIntegrationTest extends BaseIntegrationTest {
    
    @Autowired
    NumberGeneratorFacade numberGeneratorFacade;
    
    @Autowired
    ResultCheckerFacade resultCheckerFacade;
    
    @Test
    public void should_user_play_and_win_and_system_should_generate_winners() throws Exception {
        //step 1: external service returns 25 random numbers (1 to 25)
        //given & when & then
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.OK.value())
                                                           .withHeader("Content-Type", "application/json")
                                                           .withBody("""
                                                                     [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25]
                                                                     """.trim()
                                                           )));
        
        
        //step 2: it is 16.08.2024 and system fetched winning numbers for draw date: 17.08.2024 12:00
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 8, 17, 12, 0, 0);
        //when & then
        await().atMost(Duration.ofSeconds(10))
               .pollInterval(Duration.ofSeconds(1))
               .until(() -> {
                          try {
                              return !numberGeneratorFacade.retrieveWinningNumberByDate(drawDate)
                                                           .winningNumbers()
                                                           .isEmpty();
                          } catch (WinningNumbersNotFoundException e) {
                              return false;
                          }
                      }
               );
        
        
        //step 3: user made POST /inputNumbers with 6 numbers (1, 2, 3, 4, 5, 6) on 16-08-2022 12:00 and system returned OK(200) with success and ticket with draw date on 17.08.2024 12:00
        //given & when
        ResultActions performPostInputNumbers = mockMvc.perform(post("/inputNumbers")
                                                                        .content("""
                                                                                 {
                                                                                 "inputNumbers": [1,2,3,4,5,6]
                                                                                 }
                                                                                 """.trim())
                                                                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        MvcResult result = performPostInputNumbers.andExpect(status().isOk()).andReturn();
        String json = result.getResponse().getContentAsString();
        InputNumbersResultDto inputNumbersResultDto = objectMapper.readValue(json, InputNumbersResultDto.class);
        String postedTicketId = inputNumbersResultDto.ticket().ticketId();
        assertAll(
                () -> assertThat(inputNumbersResultDto.ticket().drawDate()).isEqualTo(drawDate),
                () -> assertThat(postedTicketId).isNotNull(),
                () -> assertThat(inputNumbersResultDto.message()).isEqualTo("Success")
        );
        
        
        //step 4: user made GET /results/notExistingId and system returned 404(NOT_FOUND) and body {message: Not found for id: notExistingId, status: NOT_FOUND}
        //given & when
        ResultActions performGetResultWithNotExistingId = mockMvc.perform(get("/results/notExistingId"));
        //then
        performGetResultWithNotExistingId.andExpect(status().isNotFound())
                                         .andExpect(content().json("""
                                                                   {
                                                                       "message": "Given ID: notExistingId does not exist",
                                                                       "status": "NOT_FOUND"
                                                                   }
                                                                   """.trim()
                                         ));
        
        
        //step 5: it's 5 minutes before draw date (17.08.2024 11:55)
        //given & when & then
        clock.setClockToLocalDateTime(LocalDateTime.of(2024, 8, 17, 11, 55, 0));
        
        
        //step 6: system generated result for Ticket with draw date 19.11.2022 12:00, and saved it with 6 hits
        //given & when & then
        await().atMost(10, TimeUnit.SECONDS)
               .pollInterval(Duration.ofSeconds(1))
               .until(
                       () -> {
                           try {
                               ResultDto resultDto = resultCheckerFacade.findById(postedTicketId);
                               return !resultDto.numbers().isEmpty();
                           } catch (ResultNotFoundException e) {
                               return false;
                           }
                       }
               );
        
        
        //step 7: 6 minutes passed, and it is 1 minute after draw time (17.08.2024 12:01)
        //given & when & then
        clock.plusMinutes(6);
        
        
        //step 8: user made GET /results/postedTicketId and system returned 200 (OK)
        //given & when
        ResultActions performGetResultWithExistingId = mockMvc.perform(get("/results/" + postedTicketId));
        //then
        MvcResult getResultWithExistingIdResult = performGetResultWithExistingId.andExpect(status().isOk()).andReturn();
        String getResultWithExistingIdJson = getResultWithExistingIdResult.getResponse().getContentAsString();
        ResultAnnouncerResponseDto resultDto = objectMapper.readValue(getResultWithExistingIdJson, ResultAnnouncerResponseDto.class);
        assertAll(
                () -> assertThat(resultDto.message()).isEqualTo("Congratulations, you won!"),
                () -> assertThat(resultDto.responseDto().id()).isEqualTo(postedTicketId),
                () -> assertThat(resultDto.responseDto().hitNumbers()).hasSize(6)
        );
    }
}
