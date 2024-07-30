package com.lotto.domain.numberreceiver;

enum ValidationResult {
    WRONG_NUMBERS_QUANTITY("You must give six numbers"),
    NOT_IN_RANGE("You must select numbers from 1 to 99 only"),
    SUCCESS("Success");
    
    final String message;
    
    ValidationResult(final String message) {
        this.message = message;
    }
}
