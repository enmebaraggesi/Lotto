package com.lotto.domain.numbergenerator;

import com.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;

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
    public SixRandomNumbersDto generateSixWinningNumbers() {
        return SixRandomNumbersDto.builder()
                                  .numbers(winningNumbers)
                                  .build();
    }
}
