package com.lotto.domain.numberreceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class NumberValidator {
    
    public static final int MIN_ADMISSIBLE_NUMBER = 1;
    public static final int MAX_ADMISSIBLE_NUMBER = 99;
    private static final int ACCEPTABLE_NUMBERS_QUANTITY = 6;
    private final List<ValidationResult> errors = new ArrayList<>();
    
    List<ValidationResult> validate(final Set<Integer> userNumbers) {
        if (!isSixNumbers(userNumbers)) {
            errors.add(ValidationResult.WRONG_NUMBERS_QUANTITY);
        }
        if (!isInRange(userNumbers)) {
            errors.add(ValidationResult.NOT_IN_RANGE);
        }
        return errors;
    }
    
    String generateMessage() {
        return errors.stream()
                     .map(validationResult -> validationResult.message)
                     .collect(Collectors.joining(" ,"));
    }
    
    private boolean isInRange(final Set<Integer> userNumbers) {
        return userNumbers.stream()
                          .allMatch(number -> number >= MIN_ADMISSIBLE_NUMBER && number <= MAX_ADMISSIBLE_NUMBER);
    }
    
    private boolean isSixNumbers(final Set<Integer> userNumbers) {
        return userNumbers.size() == ACCEPTABLE_NUMBERS_QUANTITY;
    }
}
