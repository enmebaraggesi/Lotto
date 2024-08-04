package com.lotto.domain.resultchecker;

import com.lotto.domain.numbergenerator.NumberGeneratorFacade;
import com.lotto.domain.numberreceiver.NumberReceiverFacade;

class ResultCheckerConfiguration {
    
    ResultCheckerFacade createForTest(final NumberGeneratorFacade numberGeneratorFacade, final NumberReceiverFacade numberReceiverFacade, final PlayerLotsRepository repository) {
        ResultCalculator calculator = new ResultCalculator();
        return new ResultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, repository, calculator);
    }
}
