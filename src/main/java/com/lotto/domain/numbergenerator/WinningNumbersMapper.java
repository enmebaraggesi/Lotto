package com.lotto.domain.numbergenerator;

import com.lotto.domain.numbergenerator.dto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.Set;

class WinningNumbersMapper {
    
    static WinningNumbersDto mapWinningNumbersToWinningNumbersDto(final WinningNumbers winningNumbers, final LocalDateTime drawDate) {
        Set<Integer> numbers = winningNumbers.winningNumbers();
        return new WinningNumbersDto(numbers, drawDate);
    }
}
