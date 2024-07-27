package com.lotto.domain.numberreceiver;

import java.util.Set;

class NumberValidator {
    
    public static final int MIN_ADMISSIBLE_NUMBER = 1;
    public static final int MAX_ADMISSIBLE_NUMBER = 99;
    private static final int ACCEPTABLE_NUMBERS_QUANTITY = 6;
    
    boolean filterAllNumbersInRange(final Set<Integer> userNumbers) {
        return userNumbers.stream()
                          .filter(number -> number >= MIN_ADMISSIBLE_NUMBER)
                          .filter(number -> number <= MAX_ADMISSIBLE_NUMBER)
                          .count() == ACCEPTABLE_NUMBERS_QUANTITY;
    }
}
