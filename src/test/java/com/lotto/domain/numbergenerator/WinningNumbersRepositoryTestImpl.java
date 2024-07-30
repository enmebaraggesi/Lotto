package com.lotto.domain.numbergenerator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class WinningNumbersRepositoryTestImpl implements WinningNumbersRepository {
    
    Map<String, WinningNumbers> inMemoryWinningNumbers = new HashMap<>();
    
    @Override
    public WinningNumbers save(final WinningNumbers winningNumbers) {
        inMemoryWinningNumbers.put(winningNumbers.id(), winningNumbers);
        return winningNumbers;
    }
    
    @Override
    public Optional<WinningNumbers> findWinningNumberByDate(final LocalDateTime drawDate) {
        return inMemoryWinningNumbers.values()
                                     .stream()
                                     .filter(number -> number.date().equals(drawDate))
                                     .findFirst();
    }
    
    @Override
    public boolean existsByDate(final LocalDateTime drawDate) {
        return inMemoryWinningNumbers.values()
                                     .stream()
                                     .anyMatch(number -> number.date().equals(drawDate));
    }
}
