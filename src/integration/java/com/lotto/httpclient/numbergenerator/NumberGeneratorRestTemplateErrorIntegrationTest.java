package com.lotto.httpclient.numbergenerator;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.lotto.domain.numbergenerator.RandomNumberGenerable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.web.server.ResponseStatusException;
import wiremock.org.apache.hc.core5.http.HttpStatus;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class NumberGeneratorRestTemplateErrorIntegrationTest {
    
    public static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    public static final String APPLICATION_JSON_CONTENT_TYPE_VALUE = "application/json";
    
    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
                                                                      .options(wireMockConfig().dynamicPort())
                                                                      .build();
    
    RandomNumberGenerable randomNumberGenerable = new NumberGeneratorRestTemplateTestConfig().remoteTestNumberGeneratorClient(wireMockServer.getPort(), 1000, 1000);
    
    @Test
    public void should_throw_500_internal_server_error_when_fault_connection_reset_by_peer() {
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.SC_OK)
                                                           .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                                                           .withFault(Fault.CONNECTION_RESET_BY_PEER))
                                       );
        //when
        Throwable throwable = catchThrowable(() -> randomNumberGenerable.generateSixWinningNumbers(1, 99, 25));
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
    
    @Test
    public void should_throw_500_internal_server_error_when_fault_empty_response() {
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.SC_OK)
                                                           .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                                                           .withFault(Fault.EMPTY_RESPONSE))
                                       );
        //when
        Throwable throwable = catchThrowable(() -> randomNumberGenerable.generateSixWinningNumbers(1, 99, 25));
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
    
    @Test
    public void should_throw_204_no_content_when_response_status_is_no_content() {
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.SC_NO_CONTENT)
                                                           .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                                                           .withBody("""
                                                                     [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25]
                                                                     """)
                                       ));
        //when
        Throwable throwable = catchThrowable(() -> randomNumberGenerable.generateSixWinningNumbers(1, 99, 25));
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("204 NO_CONTENT");
    }
    
    @Test
    void should_throw_500_internal_server_error_when_fault_malformed_response_chunk() {
        // given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.SC_OK)
                                                           .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                                                           .withFault(Fault.MALFORMED_RESPONSE_CHUNK)));
        // when
        Throwable throwable = catchThrowable(() -> randomNumberGenerable.generateSixWinningNumbers(1, 99, 25));
        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
    
    @Test
    public void should_return_zero_numbers_when_response_delay_is_higher_than_read_timeout() {
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.SC_OK)
                                                           .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                                                           .withBody("""
                                                                     [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25]
                                                                     """)
                                                           .withFixedDelay(5000)
                                       ));
        //when
        Throwable throwable = catchThrowable(() -> randomNumberGenerable.generateSixWinningNumbers(1, 99, 25));
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
}
