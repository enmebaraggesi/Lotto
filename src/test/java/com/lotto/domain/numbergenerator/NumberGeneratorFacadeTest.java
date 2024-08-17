package com.lotto.domain.numbergenerator;

import com.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import com.lotto.domain.numbergenerator.error.WinningNumbersNotFoundException;
import com.lotto.domain.numberreceiver.NumberReceiverFacade;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NumberGeneratorFacadeTest {
    
    private final WinningNumbersRepository winningNumbersRepository = new WinningNumbersRepositoryTestImpl();
    NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
    
    @Test
    public void it_should_return_set_of_required_size() {
        //given
        RandomNumberGenerable generator = new WinningNumbersGeneratorTestImpl();
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        NumberGeneratorFacade facade = new NumberGeneratorConfig().forTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        WinningNumbersDto generatedNumbers = facade.generateWinningNumbers();
        //then
        assertThat(generatedNumbers.winningNumbers().size()).isEqualTo(6);
    }
    
    @Test
    public void it_should_return_set_of_required_size_within_required_range() {
        //given
        RandomNumberGenerable generator = new WinningNumbersGeneratorTestImpl();
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        NumberGeneratorFacade facade = new NumberGeneratorConfig().forTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        WinningNumbersDto generatedNumbers = facade.generateWinningNumbers();
        //then
        int upperBand = 99;
        int lowerBand = 1;
        Set<Integer> winningNumbers = generatedNumbers.winningNumbers();
        boolean numbersInRange = winningNumbers.stream()
                                               .allMatch(number -> number >= lowerBand && number <= upperBand);
        assertThat(numbersInRange).isTrue();
    }
    
    @Test
    public void it_should_return_collection_of_unique_values() {
        //given
        RandomNumberGenerable generator = new WinningNumbersGeneratorTestImpl();
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        NumberGeneratorFacade facade = new NumberGeneratorConfig().forTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        WinningNumbersDto generatedNumbers = facade.generateWinningNumbers();
        //then
        int generatedNumbersSize = new HashSet<>(generatedNumbers.winningNumbers()).size();
        assertThat(generatedNumbersSize).isEqualTo(6);
    }
    
    @Test
    public void it_should_throw_exception_when_values_arent_within_required_range() {
        //given
        RandomNumberGenerable generator = new WinningNumbersGeneratorTestImpl(Set.of(1, 2, 3, 4, 5, 100));
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        NumberGeneratorFacade facade = new NumberGeneratorConfig().forTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        Exception caughtException = catchException(facade::generateWinningNumbers);
        //then
        assertThat(caughtException).isInstanceOf(IllegalStateException.class);
        assertThat(caughtException.getMessage()).contains("Number out of range:");
    }
    
    @Test
    public void it_should_return_winning_numbers_by_given_date() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 1, 6, 12, 0, 0);
        RandomNumberGenerable generator = new WinningNumbersGeneratorTestImpl();
        Set<Integer> generatedNumbers = generator.generateSixWinningNumbers(0,0,0).numbers();
        String id = UUID.randomUUID().toString();
        WinningNumbers winningNumbers = new WinningNumbers(id, generatedNumbers, drawDate);
        winningNumbersRepository.save(winningNumbers);
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(drawDate);
        NumberGeneratorFacade facade = new NumberGeneratorConfig().forTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        WinningNumbersDto winningNumbersDto = facade.retrieveWinningNumberByDate(drawDate);
        //then
        assertThat(winningNumbersDto.winningNumbers()).isEqualTo(generatedNumbers);
        assertThat(winningNumbersDto.date()).isEqualTo(drawDate);
    }
    
    @Test
    public void it_should_throw_exception_when_fail_to_retrieve_numbers_by_given_date() {
        //given
        RandomNumberGenerable generator = new WinningNumbersGeneratorTestImpl();
        LocalDateTime drawDate = LocalDateTime.of(2024, 1, 6, 12, 0, 0);
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(drawDate);
        NumberGeneratorFacade facade = new NumberGeneratorConfig().forTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        Exception catchException = catchException(() -> facade.retrieveWinningNumberByDate(drawDate));
        //then
        assertThat(catchException).isInstanceOf(WinningNumbersNotFoundException.class);
        assertThat(catchException.getMessage()).isEqualTo("Winning numbers not found");
    }
    
    @Test
    public void it_should_return_true_if_numbers_are_generated_by_given_date() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 1, 6, 12, 0, 0);
        RandomNumberGenerable generator = new WinningNumbersGeneratorTestImpl();
        Set<Integer> generatedNumbers = generator.generateSixWinningNumbers(0,0,0).numbers();
        String id = UUID.randomUUID().toString();
        WinningNumbers winningNumbers = new WinningNumbers(id, generatedNumbers, drawDate);
        winningNumbersRepository.save(winningNumbers);
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(drawDate);
        NumberGeneratorFacade facade = new NumberGeneratorConfig().forTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        boolean result = facade.areWinningNumbersGeneratedByDate();
        //then
        assertTrue(result);
    }
}