package com.lotto.domain.numbergenerator;

import java.util.Set;

class WinningNumbersGeneratorTestImpl implements RandomNumberGenerable {
    
    Set<Integer> winningNumbers;
    
    public WinningNumbersGeneratorTestImpl() {
        winningNumbers = Set.of(1, 2, 3, 4, 5, 6);
    }
    
    public WinningNumbersGeneratorTestImpl(final Set<Integer> numbers) {
        winningNumbers = numbers;
    }
    
    @Override
    public Set<Integer> generateSixWinningNumbers() {
        return winningNumbers;
    }
}
