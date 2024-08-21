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
    private final NumberGeneratorFacadeProperties properties;
    
    public WinningNumbersDto generateWinningNumbers() {
        Set<Integer> generatedNumbers = randomGenerator.generateSixWinningNumbers(properties.lowerBand(),
                                                                                  properties.upperBand(),
                                                                                  properties.count())
                                                       .numbers();
        numberValidator.validateGeneratedNumbers(generatedNumbers);
        String id = UUID.randomUUID().toString();
        LocalDateTime drawDate = numberReceiverFacade.retrieveNextDrawDate();
        WinningNumbers winningNumbers = new WinningNumbers(id, generatedNumbers, drawDate);
        WinningNumbers saved = repository.save(winningNumbers);
        return new WinningNumbersDto(saved.winningNumbers(), saved.date());
    }
    
    public WinningNumbersDto retrieveWinningNumberByDate(final LocalDateTime drawDate) {
        WinningNumbers winningNumbersByDate = repository.findByDate(drawDate)
                                                        .orElseThrow(() -> new WinningNumbersNotFoundException("Winning numbers not found"));
        return WinningNumbersMapper.mapWinningNumbersToWinningNumbersDto(winningNumbersByDate, drawDate);
    }
    
    boolean areWinningNumbersGeneratedByDate() {
        LocalDateTime drawDate = numberReceiverFacade.retrieveNextDrawDate();
        return repository.existsByDate(drawDate);
    }
}
