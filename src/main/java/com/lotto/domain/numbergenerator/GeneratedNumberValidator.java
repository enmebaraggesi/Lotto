package com.lotto.domain.numbergenerator;

import java.util.Set;

import static com.lotto.domain.numbergenerator.WinningNumbersGenerator.MAX_ADMISSIBLE_NUMBER;
import static com.lotto.domain.numbergenerator.WinningNumbersGenerator.MIN_ADMISSIBLE_NUMBER;

class GeneratedNumberValidator {
    
    public void validateGeneratedNumbers(final Set<Integer> numbers) {
        boolean allMatch = numbers.stream()
                                  .allMatch(number -> isHigherOrEqualToMin(number) && isLowerOrEqualToMax(number));
        if (!allMatch) {
            throw new IllegalStateException("Number out of range: " + numbers);
        }
    }
    
    private boolean isHigherOrEqualToMin(final Integer number) {
        return number >= MIN_ADMISSIBLE_NUMBER;
    }
    
    private static boolean isLowerOrEqualToMax(final Integer number) {
        return number <= MAX_ADMISSIBLE_NUMBER;
    }
}
