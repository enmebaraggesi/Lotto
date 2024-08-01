package com.lotto.domain.resultchecker;

import com.lotto.domain.numbergenerator.NumberGeneratorFacade;
import com.lotto.domain.numberreceiver.NumberReceiverFacade;

class ResultCheckerConfiguration {
    
    ResultCheckerFacade createForTest(final NumberGeneratorFacade numGenFacade, final NumberReceiverFacade numberReceiverFacade, final PlayerRepository repository) {
        return null;
    }
}
