package com.lotto.infrastructure.numbergenerator.scheduler;

import com.lotto.domain.numbergenerator.NumberGeneratorFacade;
import com.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@AllArgsConstructor
class NumberGeneratorScheduler {
    
    private final NumberGeneratorFacade facade;
    
    @Scheduled(cron = "${lotto.number-generator.scheduler.lottery-run-occurrence}")
    public void generateWinningNumbers() {
        log.info("SCHEDULER started");
        WinningNumbersDto dto = facade.generateWinningNumbers();
        log.info("Numbers: {}", dto.winningNumbers());
        log.info("Date: {}", dto.date());
    }
}
