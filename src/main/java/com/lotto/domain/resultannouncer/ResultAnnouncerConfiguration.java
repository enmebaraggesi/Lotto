package com.lotto.domain.resultannouncer;

import com.lotto.domain.numberreceiver.NumberReceiverFacade;
import com.lotto.domain.resultchecker.ResultCheckerFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ResultAnnouncerConfiguration {
    
    @Bean
    ResultAnnouncerFacade resultAnnouncerFacade(final ResultCheckerFacade resultCheckerFacade, final NumberReceiverFacade numberReceiverFacade, final ResponseRepository responseRepository, final Clock clock) {
        return new ResultAnnouncerFacade(resultCheckerFacade, numberReceiverFacade, responseRepository, clock);
    }
}
