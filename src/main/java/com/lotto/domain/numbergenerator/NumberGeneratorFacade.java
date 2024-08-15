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
    
    private final RandomNumberGenerable randomGenerator;
    private final WinningNumbersRepository repository;
    private final NumberReceiverFacade numberReceiverFacade;
    private final GeneratedNumberValidator numberValidator;
    
    public WinningNumbersDto generateWinningNumbers() {
        Set<Integer> generatedNumbers = randomGenerator.generateSixWinningNumbers().numbers();
        numberValidator.validateGeneratedNumbers(generatedNumbers);
        String id = UUID.randomUUID().toString();
        LocalDateTime drawDate = numberReceiverFacade.retrieveNextDrawDate();
        WinningNumbers winningNumbers = new WinningNumbers(id, generatedNumbers, drawDate);
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
