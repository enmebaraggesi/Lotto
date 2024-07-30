package com.lotto.domain.numbergenerator.error;

public class WinningNumbersNotFoundException extends RuntimeException {
    
    public WinningNumbersNotFoundException(String message) {
        super(message);
    }
}
