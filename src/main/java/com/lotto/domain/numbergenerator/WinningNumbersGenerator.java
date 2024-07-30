package com.lotto.domain.numbergenerator;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class WinningNumbersGenerator implements RandomNumberGenerable {
    
    public static final int MIN_ADMISSIBLE_NUMBER = 1;
    public static final int MAX_ADMISSIBLE_NUMBER = 99;
    private static final int RANDOM_BOUNDARY = MAX_ADMISSIBLE_NUMBER + 1;
    private static final int WINNING_NUMBERS_QUANTITY = 6;
    
    @Override
    public Set<Integer> generateSixWinningNumbers() {
        Random random = new SecureRandom();
        Set<Integer> winningNumbers = new HashSet<>();
        while (isSizeLowerThanSix(winningNumbers)) {
            Integer generateNumber = generateNumberBetweenMinAndMax(random);
            winningNumbers.add(generateNumber);
        }
        return winningNumbers;
    }
    
    private static boolean isSizeLowerThanSix(final Set<Integer> winningNumbers) {
        return winningNumbers.size() < WINNING_NUMBERS_QUANTITY;
    }
    
    private Integer generateNumberBetweenMinAndMax(final Random random) {
        return random.nextInt(MIN_ADMISSIBLE_NUMBER, RANDOM_BOUNDARY);
    }
}
