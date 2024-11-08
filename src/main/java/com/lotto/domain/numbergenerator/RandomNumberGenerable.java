package com.lotto.domain.numbergenerator;

import com.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;

public interface RandomNumberGenerable {
    
    SixRandomNumbersDto generateSixWinningNumbers(int lowerBand, int upperBand, int count);
}
