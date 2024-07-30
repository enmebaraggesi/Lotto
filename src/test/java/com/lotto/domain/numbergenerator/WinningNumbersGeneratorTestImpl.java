package com.lotto.domain.numbergenerator;

import java.util.Set;

class WinningNumbersGeneratorTestImpl implements RandomNumberGenerable {
    
    Set<Integer> winningNumbers;
    
    WinningNumbersGeneratorTestImpl() {
        winningNumbers = Set.of(1, 2, 3, 4, 5, 6);
    }
    
    @Override
    public Set<Integer> generateWinningNumbers() {
        return winningNumbers;
    }
}
