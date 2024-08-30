package com.lotto.domain.numberreceiver;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class NumberValidator {
    
    private static final int MIN_ADMISSIBLE_NUMBER = 1;
    private static final int MAX_ADMISSIBLE_NUMBER = 99;
    private static final int ACCEPTABLE_NUMBERS_QUANTITY = 6;
    private List<ValidationResult> errors;
    
    List<ValidationResult> validate(final Set<Integer> userNumbers) {
        errors = new LinkedList<>();
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
                     .collect(Collectors.joining(", "));
    }
    
    private boolean isInRange(final Set<Integer> userNumbers) {
        return userNumbers.stream()
                          .allMatch(number -> number >= MIN_ADMISSIBLE_NUMBER && number <= MAX_ADMISSIBLE_NUMBER);
    }
    
    private boolean isSixNumbers(final Set<Integer> userNumbers) {
        return userNumbers.size() == ACCEPTABLE_NUMBERS_QUANTITY;
    }
}
