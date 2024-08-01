package com.lotto.domain.numbergenerator;

import java.time.LocalDateTime;
import java.util.Optional;

interface WinningNumbersRepository {
    
    WinningNumbers save(WinningNumbers winningNumbers);
    
    Optional<WinningNumbers> findWinningNumberByDate(LocalDateTime drawDate);
    
    boolean existsByDate(LocalDateTime drawDate);
}
