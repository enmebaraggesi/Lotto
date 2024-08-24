package com.lotto.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.lotto.BaseIntegrationTest;
import com.lotto.domain.numbergenerator.NumberGeneratorFacade;
import com.lotto.domain.numbergenerator.error.WinningNumbersNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.awaitility.Awaitility.await;

public class UserPlayedAndWonIntegrationTest extends BaseIntegrationTest {
    
    @Autowired
    NumberGeneratorFacade facade;
    
    @Test
    public void should_user_play_and_win_and_system_should_generate_winners() {
        //step 1: external service returns 6 random numbers (1,2,3,4,5,6)
        //given
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
        //step 4: 3 days and 1 minute passed, and it is 1 minute after the draw date (19.11.2022 12:01)
        //step 5: system generated result for TicketId: sampleTicketId with draw date 19.11.2022 12:00, and saved it with 6 hits
        //step 6: 3 hours passed, and it is 1 minute after announcement time (19.11.2022 15:01)
        //step 7: user made GET /results/sampleTicketId and system returned 200 (OK)
    }
}
