package com.lotto.domain.numbergenerator;

import java.util.Collections;
import java.util.Set;

class WinningNumbersGeneratorTestImpl implements RandomNumberGenerable {
    
    Set<Integer> winningNumbers;
    
    WinningNumbersGeneratorTestImpl() {
        winningNumbers = Set.of(1,2,3,4,5,6);
    }
    
    public WinningNumbersGeneratorTestImpl(final Set<Integer> winningNumbers) {
        this.winningNumbers = winningNumbers;
    }
    
    @Override
    public Set<Integer> generateWinningNumbers() {
        return Collections.emptySet();
    }
}
