package com.lotto.infrastructure.resultchecker.scheduler;

import com.lotto.domain.numbergenerator.NumberGeneratorFacade;
import com.lotto.domain.resultchecker.ResultCheckerFacade;
import com.lotto.domain.resultchecker.dto.WinnersDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@AllArgsConstructor
class ResultCheckerScheduler {
    
    private final NumberGeneratorFacade numberGeneratorFacade;
    private final ResultCheckerFacade resultCheckerFacade;
    
    @Scheduled(cron = "${lotto.result-checker.scheduler.lottery-run-occurrence}")
    public WinnersDto generateWinningNumbers() {
        log.info("ResultCheckerScheduler started");
        if (!numberGeneratorFacade.areWinningNumbersGeneratedByDate()) {
            log.info("Winning numbers are not generated yet");
            throw new RuntimeException("Winning numbers are not generated yet");
        }
        log.info("Winning numbers are generated");
        return resultCheckerFacade.generateWinners();
    }
}
