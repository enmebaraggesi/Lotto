package com.lotto.domain.numbergenerator;

import com.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import com.lotto.domain.numberreceiver.NumberReceiverFacade;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class NumberGeneratorFacade {
    
    private final RandomNumberGenerable generator;
    private final WinningNumbersRepository winningNumbersRepository;
    private final NumberReceiverFacade numberReceiverFacade;
    
    public WinningNumbersDto generateWinningNumbers() {
        return null;
    }
    
    public WinningNumbersDto retrieveWinningNumberByDate(final LocalDateTime drawDate) {
        return null;
    }
    
    boolean areWinningNumbersGeneratedByDate() {
        return false;
    }
}
