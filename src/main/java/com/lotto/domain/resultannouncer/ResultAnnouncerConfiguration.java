package com.lotto.domain.resultannouncer;

import com.lotto.domain.resultchecker.ResultCheckerFacade;

import java.time.Clock;

class ResultAnnouncerConfiguration {
    
    ResultAnnouncerFacade createForTest(final ResultCheckerFacade resultCheckerFacade, final ResponseRepository responseRepository, final Clock clock) {
        return new ResultAnnouncerFacade(resultCheckerFacade, responseRepository, clock);
    }
}
