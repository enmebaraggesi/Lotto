package com.lotto.domain.resultchecker;

import com.lotto.domain.numbergenerator.NumberGeneratorFacade;
import com.lotto.domain.numberreceiver.NumberReceiverFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResultCheckerConfiguration {
    
    @Bean
    ResultCheckerFacade resultCheckerFacade(final NumberGeneratorFacade numberGeneratorFacade, final NumberReceiverFacade numberReceiverFacade, final PlayerLotsRepository repository) {
        ResultCalculator calculator = new ResultCalculator();
        return new ResultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, repository, calculator);
    }
}
