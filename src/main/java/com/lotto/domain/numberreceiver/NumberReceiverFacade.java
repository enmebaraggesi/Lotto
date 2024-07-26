package com.lotto.domain.numberreceiver;

import java.util.List;
import java.util.Set;

public class NumberReceiverFacade {
    
    public String inputNumbers(Set<Integer> userNumbers) {
        List<Integer> filteredNumbers = filterAllNumbersInRange(userNumbers);
        if (isQuantityOfNumbersCorrect(filteredNumbers)) {
            return "success";
        }
        return "failure";
    }
    
    private List<Integer> filterAllNumbersInRange(final Set<Integer> userNumbers) {
        return userNumbers.stream()
                          .filter(number -> number >= 1)
                          .filter(number -> number <= 99)
                          .toList();
    }
    
    private boolean isQuantityOfNumbersCorrect(final List<Integer> filteredNumbers) {
        return filteredNumbers.size() == 6;
    }
}
