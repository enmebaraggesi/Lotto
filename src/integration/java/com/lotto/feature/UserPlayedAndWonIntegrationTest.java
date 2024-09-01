package com.lotto.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.lotto.BaseIntegrationTest;
import com.lotto.domain.numbergenerator.NumberGeneratorFacade;
import com.lotto.domain.numbergenerator.error.WinningNumbersNotFoundException;
import com.lotto.domain.numberreceiver.dto.InputNumbersResultDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserPlayedAndWonIntegrationTest extends BaseIntegrationTest {
    
    @Autowired
    NumberGeneratorFacade facade;
    
    @Test
    public void should_user_play_and_win_and_system_should_generate_winners() throws Exception {
        //step 1: external service returns 6 random numbers (1,2,3,4,5,6)
        //given & when & then
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.OK.value())
                                                           .withHeader("Content-Type", "application/json")
                                                           .withBody("""
                                                                     [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25]
                                                                     """.trim()
                                                           )));
        
        
        //step 2: system fetched winning numbers for draw date: 17.08.2024 12:00
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 8, 17, 12, 0, 0);
        //when & then
        await().atMost(Duration.ofSeconds(20))
               .pollInterval(Duration.ofSeconds(1))
               .until(() -> {
                          try {
                              return !facade.retrieveWinningNumberByDate(drawDate)
                                            .winningNumbers()
                                            .isEmpty();
                          } catch (WinningNumbersNotFoundException e) {
                              return false;
                          }
                      }
               );
        
        
        //step 3: user made POST /inputNumbers with 6 numbers (1, 2, 3, 4, 5, 6) at 16-11-2022 10:00 and system returned OK(200) with message: “success” and Ticket (DrawDate:19.11.2022 12:00 (Saturday), TicketId: sampleTicketId)
        //given & when
        ResultActions performPostInpustNumbers = mockMvc.perform(post("/inputNumbers")
                                                                         .content("""
                                                                                  {
                                                                                  "inputNumbers": [1,2,3,4,5,6]
                                                                                  }
                                                                                  """.trim())
                                                                         .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        MvcResult result = performPostInpustNumbers.andExpect(status().isOk()).andReturn();
        String json = result.getResponse().getContentAsString();
        InputNumbersResultDto inputNumbersResultDto = objectMapper.readValue(json, InputNumbersResultDto.class);
        assertAll(
                () -> assertThat(inputNumbersResultDto.ticket().drawDate()).isEqualTo(drawDate),
                () -> assertThat(inputNumbersResultDto.ticket().ticketId()).isNotNull(),
                () -> assertThat(inputNumbersResultDto.message()).isEqualTo("Success")
        );
        
        
        //step 4: user made GET /results/notExistingId and system returned 404(NOT_FOUND) and body {message: Not found for id: notExistingId, status: NOT_FOUND}
        //given
        //when
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
        
        
        //step 5: 3 days and 1 minute passed, and it is 1 minute after the draw date (19.11.2022 12:01)
        //step 6: system generated result for TicketId: sampleTicketId with draw date 19.11.2022 12:00, and saved it with 6 hits
        //step 7: 3 hours passed, and it is 1 minute after announcement time (19.11.2022 15:01)
        //step 8: user made GET /results/sampleTicketId and system returned 200 (OK)
    }
}
