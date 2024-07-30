package com.lotto.domain.numbergenerator;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class WinningNumbersGenerator implements RandomNumberGenerable {
    
    private static final int MIN_ADMISSABLE_NUMBER = 1;
    private static final int MAX_ADMISSABLE_NUMBER = 99;
    private static final int RANDOM_BOUNDARY = MAX_ADMISSABLE_NUMBER + 1;
    private static final int WINNING_NUMBERS_QUANTITY = 6;
    
    @Override
    public Set<Integer> generateWinningNumbers() {
        Random random = new SecureRandom();
        Set<Integer> winningNumbers = new HashSet<>();
        while (winningNumbers.size() < WINNING_NUMBERS_QUANTITY) {
            winningNumbers.add(generateNumber(random));
        }
        return winningNumbers;
    }
    
    private Integer generateNumber(final Random random) {
        return random.nextInt(MIN_ADMISSABLE_NUMBER, RANDOM_BOUNDARY);
    }
}
