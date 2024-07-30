package com.lotto.domain.numbergenerator;

import com.lotto.domain.numberreceiver.NumberReceiverFacade;

public class NumberGeneratorConfig {
    
    NumberGeneratorFacade forTest(final RandomNumberGenerable generator, final WinningNumbersRepository winningNumbersRepository, final NumberReceiverFacade numberReceiverFacade) {
        GeneratedNumberValidator validator = new GeneratedNumberValidator();
        return new NumberGeneratorFacade(generator, winningNumbersRepository, numberReceiverFacade, validator);
    }
}
