package com.lotto.domain.numbergenerator;

import java.util.Set;


class GeneratedNumberValidator {
    
    private final static int MIN_ADMISSIBLE_NUMBER = 1;
    private final static int MAX_ADMISSIBLE_NUMBER = 99;
    
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
