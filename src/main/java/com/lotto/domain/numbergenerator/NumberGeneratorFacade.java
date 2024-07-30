package com.lotto.domain.numbergenerator;

import com.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import com.lotto.domain.numbergenerator.error.WinningNumbersNotFoundException;
import com.lotto.domain.numberreceiver.NumberReceiverFacade;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class NumberGeneratorFacade {
    
    private final RandomNumberGenerable generator;
    private final WinningNumbersRepository repository;
    private final NumberReceiverFacade numberReceiverFacade;
    
    public WinningNumbersDto generateWinningNumbers() {
        Set<Integer> generatedNumbers = generator.generateWinningNumbers();
        String id = UUID.randomUUID().toString();
        LocalDateTime drawDate = numberReceiverFacade.retrieveNextDrawDate();
        WinningNumbers winningNumbers = WinningNumbers.builder()
                                                      .id(id)
                                                      .winningNumbers(generatedNumbers)
                                                      .date(drawDate)
                                                      .build();
        repository.save(winningNumbers);
        return new WinningNumbersDto(generatedNumbers, drawDate);
    }
    
    public WinningNumbersDto retrieveWinningNumberByDate(final LocalDateTime drawDate) {
        WinningNumbers winningNumberByDate = repository.findWinningNumberByDate(drawDate)
                                                       .orElseThrow(() -> new WinningNumbersNotFoundException("Winning numbers not found"));
        return WinningNumbersMapper.mapWinningNumbersToWinningNumbersDto(winningNumberByDate, drawDate);
    }
    
    boolean areWinningNumbersGeneratedByDate() {
        LocalDateTime drawDate = numberReceiverFacade.retrieveNextDrawDate();
        return repository.existsByDate(drawDate);
    }
}
